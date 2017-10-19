# coding: utf-8
"""
    Client functions to access Lippu-API service.
"""
from __future__ import print_function
import time, datetime
import unittest
import requests
from pprint import pprint
import uuid
import base64
import logging
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5
from Crypto.Hash import SHA256
from base64 import b64encode, b64decode

character_set = "utf-8"

AUTHENTICATION_ENDPOINT = "/auth"
PRODUCTS_ENDPOINT = "/products"
AVAILABILITY_ENDPOINT = "/availability"
RESERVATION_ENDPOINT = "/reservation"

ALG_RSA_SHA256 = "RSA+SHA256"

def generate_headers(account_id, token=None, language=None):
    """
    Generate http request headers for Lippu-requests

    :param account_id: <str> Will populate X-Initiator header
    :param token: <str> If set, will populate X-Authenticator header
    :param language: <str> If set, will populate Accept-Language header
    :return: Dict of defined headers for requests

    """
    headers = {'X-Message-Id': str(uuid.uuid4()),
               'X-Transaction-Id': str(uuid.uuid4()),
               'X-Initiator': account_id,
               'content-type' :'application/json'}
    if(language != None):
        headers['Accept-Language'] = language
    if(token != None):
        headers['X-Authorization'] = token
    return headers

def product_query(base_url, headers, date=None, parameters=None):
    """
    Sends a product query request to a lippu-service.

    :param base_url: <str> Base URL for the server.
    :param headers: <dict> Headers for the request, must have authentication token etc.
    :param date: <datetime> If set, will use as date for products query. If not set,
                            current date is used.
    :param parameters: <dict> If set, will be added as query parameters for the request.
                              You can define coordinates, accessiblity options in this
                              parameter.
    :return: Requests response.

    """
    if date == None:
        logging.debug("product_query, date was None, getting current date.")
        date = datetime.datetime.now()
    datepart = date.strftime('%Y-%m-%d')
    if parameters == None:
        response = requests.get(base_url + PRODUCTS_ENDPOINT +
                            '/' + datepart,
                            headers=headers)
        return response
    else:
        response = requests.get(base_url + PRODUCTS_ENDPOINT +
                                '/' + datepart,
                                params=parameters,
                                headers=headers)
        return response

def availability_request(base_url, headers, payload):
    """
    Sends a availability post request against a server.

    :param base_url: <str> Base URL for the server.
    :param headers: <dict> Headers for the request, must have authentication token etc.
    :param payload: <dict> Request JSON body, type AvailabilityRequest.
    :return: Request response from lippu service.

    """
    logging.debug("availability_request, request payload: %s" % payload)
    response = requests.post(base_url + AVAILABILITY_ENDPOINT,
                                headers=headers,
                                json=payload)
    logging.debug("availability_request, response %s" % response)
    return response

def reservation_request(base_url, headers, payload):
    """
    Generates a reservation request against a server.

    :param base_url: <str> Base URL for the server.
    :param headers: <dict> Headers for the request, must have authentication token etc.
    :param payload: <dict> Request JSON body, type ReservationRequest.
    :return: Request response from lippu service.

    """
    logging.debug("reservation_request, request payload: %s" % payload)
    response = requests.post(base_url + RESERVATION_ENDPOINT,
                            headers=headers,
                            json=payload)
    logging.debug("reservation_request, response %s" % response)
    return response

def reservation_delete(base_url, headers, reservation_id):
    """
    Sends reservation delete request for a server with
    the given base_url.

    :param base_url: <str> Base URL for the server.
    :param headers: <dict> HTTP Headers for the request.
    :param reservation_id: <str> Reservation id to delete from lippu esrvice.
    :return: Request response from lippu service.

    """
    response = requests.delete(base_url + RESERVATION_ENDPOINT + "/" + reservation_id,
                            headers=headers)
    logging.debug("reservation_delete, response %s" % response)
    return response

def authentication_init_request(base_url, headers, account_id):
    """
    Initiates authentication flow for the Lippu-service.

    :param base_url: <str>
    :param headers: <dict> Headers for the request.
    :param account_id: <str> Clients account id for the service.
    :return: Http response from init request
    """

    # Initialize authentication
    logging.debug("authentication_init_request, start")
    response = requests.post(base_url + "/" + AUTHENTICATION_ENDPOINT + '/init',
                           headers=headers, json={'account': account_id})
    logging.debug("authentication_init_request, response %s" % response)
    return response

def authentication_commit_request(base_url, headers, snonce, key_id, key_path, alg=ALG_RSA_SHA256):
    """
    Authentication commit part, functions sends signed challenge for the lippu-service.

    :param base_url: <str> Base URL for the lippu service.
    :param headers: <dict> HTTP headers for the request.
    :param snonce: <str> Server side nonce for the authentication.
    :param key_id: <str> Server side identifier for the signing key
    :param key_path: <str> Path to the PEM-formatted key file for signing the challenge.
    :param alg: <str> Algorithm for the challenge signature.
    :return: Response of the authentication commit request.

    """

    # Commit authentication and receive authentication token.
    nonce = str(uuid.uuid4())
    nonces = snonce + nonce
    data = base64.b64encode(nonces.encode()).decode("utf-8")
    signed_data = sign_data(key_path, data)
    logging.debug("authentication_request, nonces: %s" % nonces)
    logging.debug("authentication_request, base64 encoded nonces: %s:" + data)
    logging.debug("authentication_request, signed_data: %s" % signed_data)
    commit_body = {'data':signed_data, 'pubKeyId':key_id, 'cnonce':nonce,
                   'snonce': snonce, 'alg':alg}
    response = requests.post(base_url + "/" + AUTHENTICATION_ENDPOINT  + '/commit',
                             headers=headers, json=commit_body)

    return response

def authenticate(base_url, transaction_id, account_id, key_id, key_path, alg):
    """
    Helper function to get an authentication response from the service.
    :param base_url: <str> URL for the server base
    :param transaction_id: <str> Identifier for the transaction
    :param account_id: <str> Client account id
    :param key_id: <str> Server side identifier for the signing key
    :param key_path: <str> Path to the PEM-formatted key file
    :return: Response of the authentication commit request.

    """
    logging.debug("authenticate, start")
    headers = generate_headers(account_id)
    headers['X-Transaction-Id'] = transaction_id
    r_init = authentication_init_request(base_url, headers, account_id)
    if r_init.status_code == 200 and r_init.json()['nonce'] != None:
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_commit = authentication_commit_request(base_url,
                                             headers,
                                             r_init.json()['nonce'],
                                             key_id, key_path, alg)
        return r_commit
    return None
def get_authentication_token(base_url, transaction_id, account_id, key_id, key_path):
    """
    Helper function to get an authentication token from the service.
    :param base_url: <str> URL for the authentication endpoint
    :param transaction_id: <str> Identifier for the transaction.
    :param account_id: <str> Client account id
    :param key_id: <str> Server side identifier for the signing key
    :param key_path: <str> Path to the PEM-formatted key file
    :return: String of base64 encoded signature

    """
    response = authenticate(base_url, transaction_id, account_id, key_id, key_path, ALG_RSA_SHA256)
    if(response != None
       and response.status_code == 200
       and response.json() != None
       and response.json()['token'] != None):
        return response.json()['token']
    else:
        logging.info("Failed to get authentication token is, response is %s" % response)
        return None

def sign_data(private_key_loc, data):
    '''
    Signs data with given . Expects RSA
    private key location as a parameter.

    :param private_key_loc: <str> Path to your private key
    :param data: <str> Data to be signed
    :return: base64 encoded signature
    '''
    key = open(private_key_loc, "r").read()
    rsakey = RSA.importKey(key)
    signer = PKCS1_v1_5.new(rsakey)
    digest = SHA256.new()
    digest.update(data.encode(character_set))
    sign = signer.sign(digest)
    return b64encode(sign).decode(character_set)
