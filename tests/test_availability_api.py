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
import zulu
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
        Test case for a valid availability query. Reads the
        travel properties from test-data.json, sets the
        travel dateTime two days from now to 14:00Z.
        Expects valid response for a transport leaving
        20:00+3.
        """
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
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
        product = self.testdata['test_products_current_date_response']
        travel["travel"]["dateTime"]  = zulu.now().shift(days=2).\
            replace(hour=14, minute=00).isoformat()
        r = requests.post(self.envdata['availability_url'],
                                       headers=headers, json=travel)
        logging.info("test_availability, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 200)
        self.assertEqual(len(r.json()['availability']), 3)
        self.assertEqual(r.json()['contract'], travel['contract'])
        self.assertEqual(r.json()['travel']['productType'], travel['travel']['productType'])
        self.assertEqual(r.json()['travel']['to']['lat'], travel['travel']['to']['lat'])
        self.assertEqual(r.json()['travel']['to']['lon'], travel['travel']['to']['lon'])
        self.assertEqual(r.json()['travel']['from']['lat'], travel['travel']['from']['lat'])
        self.assertEqual(r.json()['travel']['from']['lon'], travel['travel']['from']['lon'])
        for item in r.json()['availability']:
            self.assertNotEqual(item['applicableForPassengers'], 'None')
            for applicable in item['applicableForPassengers']:
                self.assertNotEqual(applicable, 'None')
                self.assertEqual(applicable['category'] in
                                 product['products']['suitablePassengerCategories'], True)
        self.assertEqual(r.json()['travel']['dateTime'].startswith(
            zulu.now().shift(days=2). \
                replace(hour=20, minute=00).format('%Y-%m-%dT%H:%M')
        ), True)
        logging.info("Departure time: %s" % zulu.parse(r.json()['travel']['dateTime']))


    def test_availability_non_valid_token(self):
        """
        Test case for using non valid authentication token for availability query

        """
        travel = self.testdata['travel_data']
        travel["travel"]["dateTime"]  = zulu.now().shift(days=2).\
            replace(hour=14, minute=00).isoformat()
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=str(uuid.uuid4()),
                                                     language="fi")
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_non_valid_token, response: %s"
                     %(r.json() ))
        self.assertEqual(r.status_code, 403)

    def test_availability_null_from_lat_coordinate(self):
        """
        Test case for using null latiatude from coordinate.

        """

        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["dateTime"]  = zulu.now().shift(days=2).\
            replace(hour=14, minute=00).isoformat()
        travel["travel"]["from"]["lat"] = None
        r = requests.post(self.envdata['availability_url'],
                                       headers=headers, json=travel)
        logging.info("test_availability_null_from_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_from_lat_coordinate_not_found(self):
        """
        Test case for using latiatude from coordinate, which
        operator does not have transport from.

        """
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["dateTime"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        travel["travel"]["from"]["lat"] = 30.5
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_from_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)
    def test_availability_null_from_lon_coordinate(self):
        """
        Test case for using null longitude from coordinate.

        """
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["from"]["lon"] = None
        travel["travel"]["dateTime"] = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_from_lon_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)


    def test_availability_null_to_lat_coordinate(self):
        """
        Test case for using null latitude to coordinate.

        """
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["to"]["lat"] = None
        travel["travel"]["dateTime"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_to_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_null_to_lon_coordinate(self):
        """
        Test case for using null longitude to coordinate.

        """
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["to"]["lon"] = None
        travel["travel"]["dateTime"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_to_lon_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)


    def test_availability_null_date(self):
        """
        Test case for using null dateTime.

        """
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["dateTime"] = None
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_null_date, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_departure_late(self):
        """
        Test case for using late daparture time,
        in which case the tansport operator does not
        have transport to offer.

        """
        token = tests.lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = tests.lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["dateTime"] = zulu.now().shift(days=2). \
            replace(hour=20, minute=45).isoformat()
        r = requests.post(self.envdata['availability_url'],
                          headers=headers, json=travel)
        logging.info("test_availability_departure_late, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)


if __name__ == '__main__':
    unittest.main()
