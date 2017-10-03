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
import logging
from tests import lippuclient
from pprint import pprint

class TestTestAPI(unittest.TestCase):
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

    def test_init_auth_non_valid_client1(self):
        """
        Test case for init_auth

        Initiates authentication process
        """
        # Initialize authentication
        headers = {'X-Message-Id': str(uuid.uuid4()),
                   'X-Transaction-Id': str(uuid.uuid4()),
                   'X-Initiator': self.testdata['non_valid_client1']}
        r_init = requests.post(self.envdata['auth_url'] + '/init',
                               headers=headers, json={'asdfadfs': 123123})
        logging.info("test_init_auth_non_valid_client1, response: %s" % r_init.text)
        self.assertEqual(r_init.status_code, 400)


if __name__ == '__main__':
    unittest.main()
