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


class TestProductsApi(unittest.TestCase):
    """ ProductsApi unit test """

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
        self.envdata = json.load(env_json)
        env_json.close()


    def tearDown(self):
        pass


    def test_products_current_date(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")
        valid_response = self.testdata['test_products_current_date_response']
        response = tests.lippuclient.product_query(self.envdata['base_url'],
                                                   headers)
        logging.info("test_products_current_date, response: %s ", response.json())
        self.assertEqual(response.status_code, 200)
        self.assertGreater(len(response.json()["products"]), 0)
        self.assertEqual(response.json()["products"][0]["contract"], valid_response["products"]["contract"])
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        self.assertEqual(response.json()["products"][0]["productType"], valid_response["products"]["productType"])
        self.assertEqual(response.json()["products"][0]["accessibility"],
                         valid_response["products"]["accessibility"])
        self.assertEqual(response.json()["products"][0]["suitablePassengerCategories"],
                         valid_response["products"]["suitablePassengerCategories"])

    def test_products_from_coordinates(self):
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()
        valid_response = self.testdata['test_products_current_date_response']
        query = {"fromLat": "60.2","fromLon":"24.8"}
        response = tests.lippuclient.product_query(self.envdata['base_url'],
                                                   headers,
                                                   t, query)
        logging.info("test_products_coordinates, response:" + response.text)

        self.assertEqual(response.status_code, 200)
        self.assertGreater(len(response.json()), 0)
        self.assertEqual(response.json()["products"][0]["contract"], valid_response["products"]["contract"])
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        self.assertEqual(response.json()["products"][0]["productType"], valid_response["products"]["productType"])
        self.assertEqual(response.json()["products"][0]["accessibility"],
                         valid_response["products"]["accessibility"])
        self.assertEqual(response.json()["products"][0]["suitablePassengerCategories"],
                         valid_response["products"]["suitablePassengerCategories"])
    def test_products_coordinates(self):

        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()
        valid_response = self.testdata['test_products_current_date_response']
        query = {"fromLat": "60.2","fromLon":"24.8",
                  "toLat": "61.0", "toLon":"25.7"}
        response = tests.lippuclient.product_query(self.envdata['base_url'],
                                                   headers,
                                                   t, query)
        logging.info("test_products_coordinates, response: %s" % response.text)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 1)
        self.assertEqual(response.json()["products"][0]["contract"], valid_response["products"]["contract"])
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        self.assertEqual(response.json()["products"][0]["productType"], valid_response["products"]["productType"])
        self.assertEqual(response.json()["products"][0]["accessibility"],
                         valid_response["products"]["accessibility"])
        self.assertEqual(response.json()["products"][0]["suitablePassengerCategories"],
                         valid_response["products"]["suitablePassengerCategories"])
    def test_products_date_in_the_past(self):
        """
        Test case for products

        Product portfolio for given datetime, date in the past (non-valid)
        """

        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")
        t = datetime.datetime.now() - datetime.timedelta(days=1)
        response = tests.lippuclient.product_query(self.envdata['base_url'],
                                                   headers,
                                                   t)
        logging.info("test_products_date_in_the_past, response: %s ", response.text)
        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json()['statusCode'], 400)

    def test_products_from_coordinates_not_found(self):

        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        t = datetime.datetime.now()
        query = {"fromLat": "63.2","fromLon":"26.2"}
        response = tests.lippuclient.product_query(self.envdata['base_url'],
                                                   headers,
                                                   t, query)
        logging.info("test_products_coordinates_not_found, response:"  + response.text)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 0)


    def test_products_non_valid_token(self):
        """
        Test case for using non valid authentication token for products query

        """
        t = datetime.datetime.now()
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=str(uuid.uuid4()),
                                                     language="fi")
        response = tests.lippuclient.product_query(self.envdata['base_url'],
                                                   headers,
                                                   t)
        logging.info("test_products_non_valid_token %s" % response .text)
        self.assertEqual(response.status_code, 403)

if __name__ == '__main__':
    unittest.main()
