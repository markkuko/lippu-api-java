# coding: utf-8

"""
    Lippu

    Transport Code broughts together legal provisions on transport market under one act.
    The LIPPU-project was created to help transport operators enable Mobility as a Service
    (MaaS) capabilities. This is API specification of LIPPU-project.
    NOTE, The ticket payment capabilities are scoped out from LIPPU-project.

    OpenAPI spec version: 0.1

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

class TestLoginApi(unittest.TestCase):
    """ LoginApi unit test stubs """

    def setUp(self):
        testdata_file='tests/testdata/testdata.json'
        testdata_json=open(testdata_file)
        self.testdata = json.load(testdata_json)
        testdata_json.close()
        env_file='tests/env.json'
        env_json=open(env_file)
        self.envdata = json.load(env_json)
        env_json.close()
        pass
    def tearDown(self):
        pass
    def test_commit_auth(self):
        """
        Test case for commit_auth

        Creates a valid authentication token
        """

        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])

        r_init = requests.post(self.envdata['auth_url'] + '/init',
                                 headers=headers, json={'account': self.testdata['valid_client1']})
        logging.info("test_commit_auth, response for init: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 200)
        self.assertNotEqual(r_init.json()['nonce'], None)

        # Commit authentication and receive authentication token.
        nonce = str(uuid.uuid4())
        headers['X-Message-Id'] = str(uuid.uuid4())
        nonces = r_init.json()['nonce'] + nonce
        data = base64.b64encode(nonces.encode()).decode("utf-8")
        commit_body = {'data':lippuclient.sign_data(self.testdata['key_path_client1'], data),
                       'pubKeyId':self.testdata['key_id_client1'],
                       'cnonce':nonce, 'snonce': r_init.json()['nonce'],'alg':'RSA+SHA256'}
        r_commit = requests.post(self.envdata['auth_url'] + '/commit',
                                 headers=headers, json=commit_body)
        logging.info("test_commit_auth, response for commit: %s" % r_commit.text)

        self.assertEqual(r_commit.status_code, 200)
        self.assertNotEqual(r_commit.json()['token'], None)
        pass


    def test_init_auth_non_valid_client1(self):
        """
        Test case for init_auth

        Initiates authentication process
        """
        # Initialize authentication
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])
        r_init = requests.post(self.envdata['auth_url'] + '/init',
                               headers=headers, json={'account': self.testdata['non_valid_client1']})

        logging.info("test_init_auth_non_valid_client1, init response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 403)
        self.assertEqual(r_init.json()['statusCode'], 403)

    def test_commit_auth_invalid_signature(self):
        """
        Test case for commit_auth

        Creates a valid authentication token
        """

        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'])
        r_init = requests.post(self.envdata['auth_url'] + '/init',
                               headers=headers, json={'account': self.testdata['valid_client1']})

        logging.info("test_commit_auth_invalid_signature, init response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 200)
        self.assertNotEqual(r_init.json()['nonce'], None)

        # Commit authentication and receive authentication token.
        nonce = str(uuid.uuid4())
        headers['X-Message-Id'] = str(uuid.uuid4())
        nonces = r_init.json()['nonce'] + nonce
        data = base64.b64encode(nonces.encode()).decode("utf-8")
        commit_body = {'data':str(uuid.uuid4()), 'pubKeyId':self.testdata['key_id_client1'],
                       'cnonce':nonce,'snonce': r_init.json()['nonce'],'alg':'RSA+SHA256'}
        r_commit = requests.post(self.envdata['auth_url'] + '/commit',
                                 headers=headers, json=commit_body)

        logging.info("test_commit_auth_invalid_signature, commit response: %s" % r_commit.text)
        self.assertEqual(r_commit.status_code, 403)
        pass
if __name__ == '__main__':
    unittest.main()
