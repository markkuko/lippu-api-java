# coding: utf-8
"""
    Client functions to access Lippu-API.
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

def get_authentication_token(auth_url, transaction_id, account_id, key_id, key_path):
    """
    Helper function to get an authentication token from the service.
    :param auth_url: <str> URL for the authentication endpoint
    :param transaction_id: <str> Identifier for the transaction
    :param account_id: <str> Client account id
    :param key_id: <str> Server side identifier for the signing key
    :param key_path: <str> Path to the PEM-formatted key file
    :return: String of base64 encoded signature

    """

    # Initialize authentication
    logging.debug("get_authentication_token, start")
    headers = generate_headers(account_id)
    r_init = requests.post(auth_url + '/init',
                           headers=headers, json={'account': account_id})

    # Commit authentication and receive authentication token.
    nonce = str(uuid.uuid4())
    nonces = r_init.json()['nonce'] + nonce
    data = base64.b64encode(nonces.encode()).decode("utf-8")
    signed_data = sign_data(key_path, data)
    logging.debug("get_authentication_token, nonces: %s" % nonces)
    logging.debug("get_authentication_token, base64 encoded nonces: %s:" + data)
    logging.debug("get_authentication_token, signed_data: %s" % signed_data)
    commit_body = {'data':signed_data, 'pubKeyId':key_id, 'cnonce':nonce,
                   'snonce': r_init.json()['nonce'], 'alg':'RSA+SHA256'}
    r_commit = requests.post(auth_url + '/commit',
                             headers=headers, json=commit_body)

    if(r_commit.status_code == 200
       and r_commit.json() != None
       and r_commit.json()['token'] != None):
        return r_commit.json()['token']
    else:
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
