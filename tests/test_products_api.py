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
from tests import lippuclient
from pprint import pprint
import requests
import logging
import os

class TestProductsApi(unittest.TestCase):
    """ ProductsApi integration tests """

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
        logging.debug("TestProductsApi: Setting target environment: %s", target_environment)
        self.envdata = json.load(env_json)[target_environment]
        env_json.close()
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        self.headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")
    def tearDown(self):
        pass


    def test_products_current_date(self):
        """
        Test API endpoint with current date. Expects
        two products in the response.

        """

        valid_response = self.testdata['test_products_current_date_response']
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers)
        logging.info("test_products_current_date, response: %s ", response.json())
        self.assertEqual(response.status_code, 200)
        self.assertGreater(len(response.json()["products"]), 0)
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        for pr in response.json()["products"]:
            if pr["contract"] is valid_response["products1"]["contract"]:
                self.assertEqual(pr["contract"]["contract"], valid_response["products1"]["contract"])
                self.assertEqual(pr["contract"]["productType"], valid_response["products1"]["productType"])
                self.assertEqual(pr["contract"]["accessibility"],
                         valid_response["products1"]["accessibility"])
                self.assertEqual(pr["contract"]["suitablePassengerCategories"],
                         valid_response["products1"]["suitablePassengerCategories"])
            if pr["contract"] is valid_response["products2"]["contract"]:
                self.assertEqual(pr["contract"]["productType"], valid_response["products2"]["productType"])
                self.assertEqual(pr["contract"]["accessibility"],
                             valid_response["products2"]["accessibility"])
                self.assertEqual(pr["contract"]["extraServices"],
                                 valid_response["products2"]["extraServices"])
                self.assertEqual(pr["contract"]["suitablePassengerCategories"],
                             valid_response["products2"]["suitablePassengerCategories"])

    def test_products_from_coordinates(self):
        """
        Test API endpoint with current date and from coordinates. Expects
        one product in the response.

        """

        t = datetime.datetime.now()
        valid_response = self.testdata['test_products_current_date_response']
        query = {"fromLat": "60.2","fromLon":"24.8"}
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t, query)
        logging.info("test_products_coordinates, response:" + response.text)

        self.assertEqual(response.status_code, 200)
        self.assertGreater(len(response.json()), 0)
        self.assertEqual(len(response.json()["products"]), 1)
        self.assertEqual(response.json()["products"][0]["contract"],
                         valid_response["products1"]["contract"])
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        self.assertEqual(response.json()["products"][0]["productType"],
                         valid_response["products1"]["productType"])
        self.assertEqual(response.json()["products"][0]["accessibility"],
                         valid_response["products1"]["accessibility"])
        self.assertEqual(response.json()["products"][0]["suitablePassengerCategories"],
                         valid_response["products1"]["suitablePassengerCategories"])

    def test_products_to_coordinates(self):
        """
        Test API endpoint with next monday and to coordinates. Expects
        one product in the response.

        """

        t = datetime.datetime.now()
        t = t + datetime.timedelta(days=(7 - t.weekday()))
        valid_response = self.testdata['test_products_current_date_response']
        query = {"toLat": "60.5","toLon":"26.9"}
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t, query)
        logging.info("test_products_to_coordinates, response:" + response.text)

        self.assertEqual(response.status_code, 200)
        self.assertGreater(len(response.json()), 0)
        self.assertEqual(len(response.json()["products"]), 1)
        self.assertEqual(response.json()["products"][0]["contract"],
                         valid_response["products2"]["contract"])
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        self.assertEqual(response.json()["products"][0]["productType"],
                         valid_response["products2"]["productType"])
        self.assertEqual(response.json()["products"][0]["accessibility"],
                         valid_response["products2"]["accessibility"])
        self.assertEqual(response.json()["products"][0]["suitablePassengerCategories"],
                         valid_response["products2"]["suitablePassengerCategories"])
        self.assertEqual(response.json()["products"][0]["extraServices"],
                         valid_response["products2"]["extraServices"])
    def test_products_coordinates(self):


        t = datetime.datetime.now()
        valid_response = self.testdata['test_products_current_date_response']
        query = {"fromLat": "60.2","fromLon":"24.8",
                  "toLat": "61.0", "toLon":"25.7"}
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t, query)
        logging.info("test_products_coordinates, response: %s" % response.text)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 1)
        self.assertEqual(response.json()["products"][0]["contract"], valid_response["products1"]["contract"])
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        self.assertEqual(response.json()["products"][0]["productType"], valid_response["products1"]["productType"])
        self.assertEqual(response.json()["products"][0]["accessibility"],
                         valid_response["products1"]["accessibility"])
        self.assertEqual(response.json()["products"][0]["suitablePassengerCategories"],
                         valid_response["products1"]["suitablePassengerCategories"])
    def test_products_date_in_the_past(self):
        """
        Test case for products

        Product portfolio for given datetime, date in the past (non-valid)
        """
        t = datetime.datetime.now() - datetime.timedelta(days=1)
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t)
        logging.info("test_products_date_in_the_past, response: %s ", response.text)
        self.assertEqual(response.status_code, 400)
        self.assertEqual(response.json()['statusCode'], 400)

    def test_products_from_coordinates_not_found(self):

        t = datetime.datetime.now()
        query = {"fromLat": "63.2","fromLon":"26.2"}
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t, query)
        logging.info("test_products_coordinates_not_found, response:"  + response.text)

        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 0)


    def test_products_invalid_accessibility(self):
        """
        Test API endpoint with current date and

        """
        valid_response = self.testdata['test_products_current_date_response']
        query = {"accessibility": ["PUSHCHAIR", "TESTING"]}
        t = datetime.datetime.now()
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t, query)
        logging.info("test_products_invalid_accessibility, response: %s ", response.json())
        self.assertEqual(response.status_code, 400)

    def test_products_non_valid_token(self):
        """
        Test case for using non valid authentication token for products query

        """
        t = datetime.datetime.now()
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=str(uuid.uuid4()),
                                                     language="fi")
        response = lippuclient.product_query(self.envdata['base_url'],
                                                   headers,
                                                   t)
        logging.info("test_products_non_valid_token %s" % response .text)
        self.assertEqual(response.status_code, 403)

    def test_products_filter_accessibility(self):
        """
        Test API endpoint with next monday and request
        wheelchair access. Expect one product from endpoint.

        """
        t = datetime.datetime.now()
        t = t + datetime.timedelta(days=(7 - t.weekday()))

        query = {"accessibility": ["GUIDE_DOG"]}
        valid_response = self.testdata['test_products_current_date_response']
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t,query)
        logging.info("test_products_filter_accessibility, response: %s ", response.json())
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 1)
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])
        for pr in response.json()["products"]:
            if pr["contract"] is valid_response["products2"]["contract"]:
                self.assertEqual(pr["contract"]["productType"],
                                 valid_response["products2"]["productType"])
                self.assertEqual(valid_response["products2"]["accessibility"][0]
                            in pr["contract"]["accessibility"], True)
                self.assertEqual(valid_response["products2"]["accessibility"][1]
                                 in pr["contract"]["accessibility"], True)
                self.assertEqual(pr["contract"]["extraServices"],
                                 valid_response["products2"]["extraServices"])
                self.assertEqual(pr["contract"]["suitablePassengerCategories"],
                                 valid_response["products2"]["suitablePassengerCategories"])

    def test_products_filter_accessibility(self):
        """
        Test API endpoint with next monday and request
        wheelchair and step_free_access access. Expect
        0 products as a result.

        """
        t = datetime.datetime.now()
        t = t + datetime.timedelta(days=(7 - t.weekday()))

        query = {"accessibility": ["GUIDE_DOG", "STEP_FREE_ACCESS"]}
        valid_response = self.testdata['test_products_current_date_response']
        response = lippuclient.product_query(self.envdata['base_url'],
                                             self.headers,
                                                   t,query)
        logging.info("test_products_filter_accessibility, response: %s ", response.json())
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.json()["products"]), 0)
        self.assertEqual(response.json()["passengerCategories"],
                         valid_response["passengerCategories"])


if __name__ == '__main__':
    unittest.main()
