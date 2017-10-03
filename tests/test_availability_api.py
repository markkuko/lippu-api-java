# coding: utf-8

"""
    Lippu

    Transport Code broughts together legal provisions on transport market under one act.
    The LIPPU-project was created to help transport operators enable Mobility as a Service
    (MaaS) capabilities. This is API specification of LIPPU-project.
    NOTE, The ticket payment capabilities are scoped out from LIPPU-project.

    OpenAPI spec version: 0.1

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
        r_availability = requests.post(self.envdata['availability_url'],
                                       headers=headers, json=travel)
        logging.info("test_make_reservation_and_delete, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

    def test_products_date_in_the_past(self):
        """
        Test case for products

        Product portfolio for given datetime, date in the past (non-valid)
        """

        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")
        t = datetime.datetime.now() - datetime.timedelta(days=1)
        response = requests.get(self.envdata['products_url'] + '/' + t.strftime('%Y-%m-%d'),
                                  headers=headers)
        logging.info("test_products_date_in_the_past, response: %s ", response.text)
        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json()['statusCode'], 400)

    def test_products_current_date(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()
        response = requests.get(self.envdata['products_url'] + '/' + t.strftime('%Y-%m-%d'),
                                  headers=headers)
        logging.info("test_products_current_date, response: %s ", response.text)
        self.assertEqual(response.status_code, 200)
        self.assertGreater(len(response.json()), 0)

    def test_products_from_coordinates(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()
        query = {"fromLat": "60.2","fromLon":"24.8"}
        response = requests.get(self.envdata['products_url'] + '/'
                                + t.strftime('%Y-%m-%d'),
                                    params=query,
                                    headers=headers)
        logging.info("test_products_coordinates, response:" + response.text)

        self.assertEqual(response.status_code, 200)
        self.assertGreater(len(response.json()), 0)

    def test_products_from_coordinates_not_found(self):

        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()
        query = {"fromLat": "63.2","fromLon":"26.2"}
        response = requests.get(self.envdata['products_url'] + '/' + t.strftime('%Y-%m-%d'),
                                    params=query,
                                    headers=headers)
        logging.info("test_products_coordinates_not_found, response:"  + response.text)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 0)

    def test_products_coordinates(self):

        token = tests.lippuclient.get_authentication_token(self.envdata['auth_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()

        query = {"fromLat": "60.2","fromLon":"24.8",
                  "toLat": "61.0", "toLon":"25.7"}
        response = requests.get(self.envdata['products_url'] + '/' + t.strftime('%Y-%m-%d'),
                                    params=query,
                                    headers=headers)
        logging.info("test_products_coordinates, response: %s" % response.text)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 1)


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
             %(r.text ))
        self.assertEqual(r.status_code, 403)


    def test_products_non_valid_token(self):
        """
        Test case for using non valid authentication token for products query

        """
        t = datetime.datetime.now()
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=str(uuid.uuid4()),
                                                     language="fi")
        r = requests.get(self.envdata['products_url'] + '/' + t.strftime('%Y-%m-%d'),
                                  headers=headers)
        logging.info("test_products_non_valid_token %s" % r.text)
        self.assertEqual(r.status_code, 403)

if __name__ == '__main__':
    unittest.main()
