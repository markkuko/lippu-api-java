# coding: utf-8

"""
    Lippu

    Transport Code broughts together legal provisions on transport market under one act.
    The LIPPU-project was created to help transport operators enable Mobility as a Service
    (MaaS) capabilities. This is API specification of LIPPU-project.
    NOTE, The ticket payment capabilities are scoped out from LIPPU-project.

    OpenAPI spec version: 0.2

"""

from __future__ import absolute_import
from __future__ import print_function

import os
import sys
import unittest
import time, datetime
import uuid
import json
import base64
import requests
from tests import lippuclient
from pprint import pprint
import logging
import zulu

class TestLoginApi(unittest.TestCase):
    """ LoginApi unit test stubs """

    def setUp(self):
        """
        Set up test data for the test cases.
        """

        testdata_file='tests/testdata/testdata.json'
        testdata_json=open(testdata_file)
        self.testdata = json.load(testdata_json)
        testdata_json.close()
        env_file='tests/env.json'
        env_json=open(env_file)
        target_environment= os.getenv('target_environment', 'test')
        logging.debug("TestLoginApi: Setting target environment %s", target_environment)
        self.envdata = json.load(env_json)[target_environment]
        env_json.close()
        pass
    def tearDown(self):
        """
        Tear down.
        """
        pass
    def test_commit_auth(self):
        """
        Test case for successful init_auth and commit_auth

        """
        t = zulu.now()
        account_id=self.testdata['valid_client1']

        headers = lippuclient.generate_headers(account_id=account_id)

        r_init = lippuclient.authentication_init_request(self.envdata['base_url'],
                                                         headers,
                                                         account_id)
        #r_init = requests.post(self.envdata['auth_url'] + '/init',
        #                         headers=headers, json={'account': account_id})
        logging.info("test_commit_auth, response for init: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 200)
        self.assertNotEqual(r_init.json()['nonce'], None)
        self.assertEqual(r_init.json()['user'], account_id)
        self.assertNotEqual(r_init.json()['expires'], None)
        self.assertEqual(zulu.parse(r_init.json()['expires']) > t, True)

        # Commit authentication and receive authentication token.
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_commit = lippuclient.authentication_commit_request(self.envdata['base_url'],
                                                  headers,
                                                  r_init.json()['nonce'],
                                                  self.testdata['key_id_client1'],
                                                  self.testdata['key_path_client1'],
                                                  lippuclient.ALG_RSA_SHA256)
        logging.info("test_commit_auth, response for commit: %s" % r_commit.text)

        self.assertEqual(r_commit.status_code, 200)
        self.assertNotEqual(r_commit.json()['token'], None)
        self.assertNotEqual(r_commit.json()['expires'], None)
        self.assertEqual(r_commit.json()['user'], account_id)
        self.assertEqual(zulu.parse(r_commit.json()['expires']) > t, True)
        self.assertEqual(zulu.parse(r_commit.json()['expires']) >
                         zulu.parse(r_init.json()['expires']), True)
        pass

    def test_authenticate(self):
        """
        Test case for successful authentication

        """
        t = zulu.now()
        account_id=self.testdata['valid_client1']

        response = lippuclient.authenticate(self.envdata['base_url'],
                                    str(uuid.uuid4()),
                                    account_id,
                                    self.testdata['key_id_client1'],
                                    self.testdata['key_path_client1'],
                                    lippuclient.ALG_RSA_SHA256)
        logging.info("test_authenticate, response is: %s" % response.text)

        self.assertEqual(response.status_code, 200)
        self.assertNotEqual(response.json()['token'], None)
        self.assertNotEqual(response.json()['expires'], None)
        self.assertEqual(response.json()['user'], account_id)
        self.assertEqual(zulu.parse(response.json()['expires']) > t, True)
        pass


    def test_init_auth_non_valid_client1(self):
        """
        Test case for init_auth

        Initiates authentication process with account, that does not
        exists.
        """
        # Initialize authentication
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])
        r_init = lippuclient.authentication_init_request(self.envdata['base_url'],
                               headers=headers, account_id=self.testdata['non_valid_client1'])

        logging.info("test_init_auth_non_valid_client1, init response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 403)
        self.assertEqual(r_init.json()['statusCode'], 403)

    def test_init_auth_null_client(self):
        """
        Test case for init_auth

        Initiates authentication process with null as account.
        """
        # Initialize authentication
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])
        r_init = lippuclient.authentication_init_request(self.envdata['base_url'],
                                                         headers=headers, account_id=None)

        logging.info("test_init_auth_non_valid_client1, init response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 400)
        self.assertEqual(r_init.json()['statusCode'], 400)
    def test_commit_auth_invalid_signature(self):
        """
        Test case for commit_auth

        Creates a valid authentication token
        """

        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])
        r_init = lippuclient.authentication_init_request(self.envdata['base_url'],
                               headers=headers, account_id=self.testdata['valid_client1'])

        logging.info("test_commit_auth_invalid_signature, init response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 200)
        self.assertNotEqual(r_init.json()['nonce'], None)

        # Commit authentication and receive authentication token.
        nonce = str(uuid.uuid4())
        headers['X-Message-Id'] = str(uuid.uuid4())
        nonces = r_init.json()['nonce'] + nonce
        commit_body = {'data':str(uuid.uuid4()), 'pubKeyId':self.testdata['key_id_client1'],
                       'cnonce':nonce,'snonce': r_init.json()['nonce'],'alg':'RSA+SHA256'}
        r_commit = requests.post(self.envdata['base_url'] + lippuclient.AUTHENTICATION_COMMIT_ENDPOINT,
                                 headers=headers, json=commit_body)

        logging.info("test_commit_auth_invalid_signature, commit response: %s" % r_commit.text)
        self.assertEqual(r_commit.status_code, 403)
        pass

    def test_commit_auth_keyid_not_found(self):
        """
        Test case for commit_auth.
        Tests authentication with keyId that does not exists
        """

        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])
        r_init = lippuclient.authentication_init_request(self.envdata['base_url'],
                                                 headers=headers,
                                                 account_id=self.testdata['valid_client1'])


        logging.info("test_commit_auth_keyid_not_found, init response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 200)
        self.assertNotEqual(r_init.json()['nonce'], None)

        # Commit authentication and receive authentication token.
        nonce = str(uuid.uuid4())
        headers['X-Message-Id'] = str(uuid.uuid4())
        nonces = r_init.json()['nonce'] + nonce
        data = base64.b64encode(nonces.encode()).decode("utf-8")
        commit_body = {'data':data, 'pubKeyId':self.testdata['non_valid_key_id_client1'],
                       'cnonce':nonce,'snonce': r_init.json()['nonce'],'alg':'RSA+SHA256'}
        r_commit = requests.post(self.envdata['base_url'] +
                                 lippuclient.AUTHENTICATION_COMMIT_ENDPOINT,
                                 headers=headers, json=commit_body)

        logging.info("test_commit_auth_keyid_not_found, commit response: %s" % r_commit.text)
        self.assertEqual(r_commit.status_code, 403)
        pass

    def test_commit_auth_keyid_null(self):
        """
        Test case for commit_auth.
        Tests authentication with keyId that does not exists
        """

        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])
        r_init = lippuclient.authentication_init_request(self.envdata['base_url'],
                               headers=headers, account_id=self.testdata['valid_client1'])

        logging.info("test_commit_auth_keyid_null, init response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 200)
        self.assertNotEqual(r_init.json()['nonce'], None)

        # Commit authentication and receive authentication token.
        headers['X-Message-Id'] = str(uuid.uuid4())
        nonce = str(uuid.uuid4())
        nonces =  r_init.json()['nonce'] + nonce
        data = base64.b64encode(nonces.encode()).decode("utf-8")
        commit_body = {'data':data, 'pubKeyId': None,
                       'cnonce':nonce,'snonce': r_init.json()['nonce'],'alg':'RSA+SHA256'}
        r_commit = requests.post(self.envdata['base_url'] +
                                 lippuclient.AUTHENTICATION_COMMIT_ENDPOINT,
                                 headers=headers, json=commit_body)

        logging.info("test_commit_auth_keyid_not_found, commit response: %s" % r_commit.text)
        self.assertEqual(r_commit.status_code, 400)
        pass

if __name__ == '__main__':
    unittest.main()
