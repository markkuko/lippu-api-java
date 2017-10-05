# coding: utf-8

"""
    Lippu

    Transport Code broughts together legal provisions on transport market under one act.
    The LIPPU-project was created to help transport operators enable Mobility as a Service
    (MaaS) capabilities. This is API specification of LIPPU-project.
    NOTE, The ticket payment capabilities are scoped out from LIPPU-project.

"""
from __future__ import absolute_import, print_function

import datetime
import unittest
import uuid
import json
from pprint import pprint
import requests
import logging
import tests.lippuclient


class TestAvailabilityApi(unittest.TestCase):
    """ AvailabilityApi unit test stubs """

    def setUp(self):
        testdata_file='tests/testdata/testdata.json'
        testdata_json=open(testdata_file)
        self.testdata = json.load(testdata_json)
        testdata_json.close()
        env_file='tests/env.json'
        env_json=open(env_file)
        self.envdata = json.load(env_json)
        env_json.close()


    def tearDown(self):
        pass

    def test_availability(self):
        """
        Test case for availability

        Trip availaibility inquiry
        """
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()

        # Trip availaibility inquiry
        travel = self.testdata['travel_data']
        r = requests.post(self.envdata['availability_url'],
                                       headers=headers, json=travel)
        logging.info("test_availability, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 200)


    def test_availability_non_valid_token(self):
        """
        Test case for using non valid authentication token for availability query

        """
        travel = self.testdata['travel_data']
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=str(uuid.uuid4()),
                                                     language="fi")
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_non_valid_token, response: %s"
                     %(r.json() ))
        self.assertEqual(r.status_code, 403)

    def test_availability_null_from_lat_coordinate(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["from"]["lat"] = None
        r = requests.post(self.envdata['availability_url'],
                                       headers=headers, json=travel)
        logging.info("test_availability_null_from_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_null_from_lon_coordinate(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["from"]["lon"] = None
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_from_lon_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)


    def test_availability_null_to_lat_coordinate(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["to"]["lat"] = None
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_to_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_null_to_lon_coordinate(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["to"]["lon"] = None
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_to_lon_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_null_date(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["datetime"] = None
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_date, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)


if __name__ == '__main__':
    unittest.main()
