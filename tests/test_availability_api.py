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
import os
from tests import lippuclient

class TestAvailabilityApi(unittest.TestCase):
    """ AvailabilityApi unit test stubs """

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
        logging.debug("TestAvailabilityApi: Setting target environment %s", target_environment)
        self.envdata = json.load(env_json)[target_environment]
        env_json.close()


    def tearDown(self):
        """
        Tear down test data.
        """
        pass

    def test_availability_departure_time(self):
        """
        Test case for a valid availability query. Reads the
        travel properties from test-data.json, sets the
        travel departureTimeEarliest two days from now to 14:00Z.
        Expects valid response for a transport leaving
        20:00+3.
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data']
        product = self.testdata['test_products_current_date_response']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2).\
            replace(hour=14, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                                       headers=headers, payload=travel)
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
                                 product['products1']['suitablePassengerCategories'], True)
        self.assertEqual(r.json()['travel']['departureTime'].startswith(
            zulu.now().shift(days=2). \
                replace(hour=20, minute=00).format('%Y-%m-%dT%H:%M')
        ), True)
        logging.info("Departure time: %s" % zulu.parse(r.json()['travel']['departureTime']))


    def test_availability_arrival_time(self):
        """
        Test case for a valid availability query. Reads the
        travel properties from test-data.json, sets the
        travel arrivalTimeLatest two days from now to 23:30Z.
        Expects valid response for a transport leaving
        20:00+3.
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data']
        product = self.testdata['test_products_current_date_response']
        travel["travel"]["departureTimeEarliest"] = None
        travel["travel"]["arrivalTimeLatest"]  = zulu.now().shift(days=2). \
            replace(hour=23, minute=30).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                                             headers=headers, payload=travel)
        logging.info("test_availability_arrival_time, availability response: %s"
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
                                 product['products1']['suitablePassengerCategories'], True)
        self.assertEqual(r.json()['travel']['arrivalTime'].startswith(
            zulu.now().shift(days=2). \
                replace(hour=23, minute=10).format('%Y-%m-%dT%H:%M')
        ), True)
        logging.info("Arrival time: %s" % zulu.parse(r.json()['travel']['arrivalTime']))

    def test_availability_set_both_times(self):
        """
        Test case for a valid availability query. Reads the
        travel properties from test-data.json, sets the
        travel departureTimeEarliest two days from now to 14:00Z
        and arrivalTimeLatest two days from now at 21:30.
        As both are set, expects the departureTimeEarliest
        to be used for query.Expects valid response for a transport leaving
        20:00+3.
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data']
        product = self.testdata['test_products_current_date_response']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        travel["travel"]["arrivalTimeLatest"]  = zulu.now().shift(days=2). \
            replace(hour=21, minute=30).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                                             headers=headers, payload=travel)
        logging.info("test_availability_both_arrival_time, availability response: %s"
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
                                 product['products1']['suitablePassengerCategories'], True)

        self.assertEqual(r.json()['travel']['departureTime'].startswith(
            zulu.now().shift(days=2). \
                replace(hour=20, minute=00).format('%Y-%m-%dT%H:%M')
        ), True)
        self.assertEqual(r.json()['travel']['arrivalTime'].startswith(
            zulu.now().shift(days=2). \
                replace(hour=23, minute=10).format('%Y-%m-%dT%H:%M')
        ), True)

        logging.info("Arrival time: %s" % zulu.parse(r.json()['travel']['arrivalTime']))
    def test_availability_non_valid_token(self):
        """
        Test case for using non valid authentication token for availability query

        """
        travel = self.testdata['travel_data']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2).\
            replace(hour=14, minute=00).isoformat()
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=str(uuid.uuid4()),
                                                     language="fi")
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_non_valid_token, response: %s"
                     %(r.json() ))
        self.assertEqual(r.status_code, 403)

    def test_availability_null_from_lat_coordinate(self):
        """
        Test case for using null latiatude from coordinate.

        """

        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2).\
            replace(hour=14, minute=00).isoformat()
        travel["travel"]["from"]["lat"] = None
        r = lippuclient.availability_request(self.envdata['base_url'],
                                       headers=headers, payload=travel)
        logging.info("test_availability_null_from_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_from_lat_coordinate_not_found(self):
        """
        Test case for using latiatude from coordinate, which
        operator does not have transport from.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        travel["travel"]["from"]["lat"] = 30.5
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_null_from_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)
    def test_availability_null_from_lon_coordinate(self):
        """
        Test case for using null longitude from coordinate.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["from"]["lon"] = None
        travel["travel"]["departureTimeEarliest"] = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_null_from_lon_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)


    def test_availability_null_to_lat_coordinate(self):
        """
        Test case for using null latitude to coordinate.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["to"]["lat"] = None
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_null_to_lat_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_null_to_lon_coordinate(self):
        """
        Test case for using null longitude to coordinate.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["to"]["lon"] = None
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_null_to_lon_coordinate, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)


    def test_availability_null_date(self):
        """
        Test case for using null departureTimeEarliest.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["departureTimeEarliest"] = None
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_null_date, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 200)


    def test_availability_departure_late(self):
        """
        Test case for using too late departure time earliest,
        in which case the tansport operator does not
        have transport to offer.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["departureTimeEarliest"] = zulu.now().shift(days=2). \
            replace(hour=20, minute=45).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_departure_late, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)

    def test_availability_arrival_early(self):
        """
        Test case for using too early arrival time latest,
        in which case the tansport operator does not
        have transport to offer.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")

        travel = self.testdata['travel_data']
        travel["travel"]["departureTimeEarliest"] = None
        travel["travel"]["arrivalTimeLatest"] = zulu.now().shift(days=2). \
            replace(hour=20, minute=45).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                                             headers=headers, payload=travel)
        logging.info("test_availability_arrival_early, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)
        self.assertEqual(r.json()["statusCode"], 400)
    def test_availability_accessibility(self):
        """
        Test case for a valid availability query. Reads the
        travel properties from test-data.json, sets the
        travel departureTimeEarliest to next monday at 11:00Z.
        Expects valid response for a transport leaving
        19:00+3, one availability for the passenger adult with
        WHEELCHAIR accessibility feature in the reservation.
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data_accessibility']
        product = self.testdata['test_products_current_date_response']
        t = datetime.datetime.now()
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=11, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_accessibility, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 200)
        self.assertEqual(len(r.json()['availability']), 1)
        self.assertEqual(r.json()['contract'], travel['contract'])
        self.assertEqual(r.json()['travel']['productType'], travel['travel']['productType'])
        self.assertEqual(r.json()['travel']['to']['lat'], travel['travel']['to']['lat'])
        self.assertEqual(r.json()['travel']['to']['lon'], travel['travel']['to']['lon'])
        self.assertEqual(r.json()['travel']['from']['lat'], travel['travel']['from']['lat'])
        self.assertEqual(r.json()['travel']['from']['lon'], travel['travel']['from']['lon'])
        for item in r.json()['availability']:
            self.assertNotEqual(item['applicableForPassengers'], 'None')
            self.assertEqual(item['fare']['currency'], 'EUR')
            self.assertEqual(item['fare']['amount'], 14)
            self.assertEqual(item['fare']['vatPercent'], 10)
            for applicable in item['applicableForPassengers']:
                self.assertEqual(applicable['category'], 'Adult')
                self.assertEqual(applicable['category'] in
                                 product['products2']['suitablePassengerCategories'], True)
                self.assertEqual(applicable['accessibility'][0]['title'], 'WHEELCHAIR')
                self.assertEqual(applicable['accessibility'][0]['fare']['currency'], 'EUR')
                self.assertEqual(applicable['accessibility'][0]['fare']['amount'], 0)
                self.assertEqual(applicable['accessibility'][0]['fare']['vatPercent'], 0)
        self.assertEqual(r.json()['travel']['departureTime'].startswith(
            zulu.now().shift(days=(7 - t.weekday())). \
                replace(hour=19, minute=00).format('%Y-%m-%dT%H:%M')
        ), True)
        logging.info("Departure time: %s" % zulu.parse(r.json()['travel']['departureTime']))

    def test_availability_extra_services(self):
        """
        Test case for a valid availability query with valid extraService. Reads the
        travel properties from test-data.json, sets the
        travel departureTimeEarliest to next monday at 11:00Z.
        Expects valid response for a transport leaving
        19:00+3, one availability for the passenger child with
        CHILDSEAT extra service.
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data_extra_services']
        product = self.testdata['test_products_current_date_response']
        t = datetime.datetime.now()
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=11, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_extra_services, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 200)
        self.assertEqual(len(r.json()['availability']), 1)
        self.assertEqual(r.json()['contract'], travel['contract'])
        self.assertEqual(r.json()['travel']['productType'], travel['travel']['productType'])
        self.assertEqual(r.json()['travel']['to']['lat'], travel['travel']['to']['lat'])
        self.assertEqual(r.json()['travel']['to']['lon'], travel['travel']['to']['lon'])
        self.assertEqual(r.json()['travel']['from']['lat'], travel['travel']['from']['lat'])
        self.assertEqual(r.json()['travel']['from']['lon'], travel['travel']['from']['lon'])
        for item in r.json()['availability']:
            self.assertNotEqual(item['applicableForPassengers'], 'None')
            self.assertEqual(item['fare']['currency'], 'EUR')
            self.assertEqual(item['fare']['amount'], 14)
            self.assertEqual(item['fare']['vatPercent'], 10)
            for applicable in item['applicableForPassengers']:
                self.assertEqual(applicable['category'], 'Child')
                self.assertEqual(applicable['category'] in
                                 product['products2']['suitablePassengerCategories'], True)
                self.assertEqual(applicable['extraServices'][0]['title'], 'CHILDSEAT')
                self.assertNotEqual(applicable['extraServices'][0]['extraServiceReservationId'], None)
                self.assertNotEqual(applicable['extraServices'][0]['extraServiceReservationId'],
                                    "EXTRA-CHILD-SEAT-115311")
                self.assertEqual(applicable['extraServices'][0]['extraServiceReservationId']
                                 .startswith("EXTRA-CHILDSEAT-"), True)
                self.assertEqual(applicable['extraServices'][0]['fare']['currency'], 'EUR')
                self.assertEqual(applicable['extraServices'][0]['fare']['amount'], 13)
                self.assertEqual(applicable['extraServices'][0]['fare']['vatPercent'], 10)
        self.assertEqual(r.json()['travel']['departureTime'].startswith(
            zulu.now().shift(days=(7 - t.weekday())). \
                replace(hour=19, minute=00).format('%Y-%m-%dT%H:%M')
        ), True)
        logging.info("Departure time: %s" % zulu.parse(r.json()['travel']['departureTime']))

    def test_availability_invalid_extra_services(self):
        """
        Test case for a availability query with invalid extraService.
        Expects valid response for a transport leaving, but with
        no availabilities as the one passenger has invalid
        extraService
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data_extra_services']
        travel['passengers'][0]['extraServices'][0]['title'] = "TESTINGINVALIDSERVICE"
        product = self.testdata['test_products_current_date_response']
        t = datetime.datetime.now()
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=11, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_invalid_extra_services, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 200)
        self.assertEqual(len(r.json()['availability']), 0)
        self.assertEqual(r.json()['contract'], travel['contract'])
        self.assertEqual(r.json()['travel']['productType'], travel['travel']['productType'])
        self.assertEqual(r.json()['travel']['to']['lat'], travel['travel']['to']['lat'])
        self.assertEqual(r.json()['travel']['to']['lon'], travel['travel']['to']['lon'])
        self.assertEqual(r.json()['travel']['from']['lat'], travel['travel']['from']['lat'])
        self.assertEqual(r.json()['travel']['from']['lon'], travel['travel']['from']['lon'])

    def test_availability_empty_extra_services(self):
        """
        Test case for a availability query with invalid extraService.
        Expects valid response for a transport leaving, but with
        no availabilities as the one passenger has invalid
        extraService
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data_extra_services']
        travel['passengers'][0]['extraServices'][0]['title'] = ""
        product = self.testdata['test_products_current_date_response']
        t = datetime.datetime.now()
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=11, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_invalid_extra_services, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 200)
        self.assertEqual(len(r.json()['availability']), 0)
        self.assertEqual(r.json()['contract'], travel['contract'])
        self.assertEqual(r.json()['travel']['productType'], travel['travel']['productType'])
        self.assertEqual(r.json()['travel']['to']['lat'], travel['travel']['to']['lat'])
        self.assertEqual(r.json()['travel']['to']['lon'], travel['travel']['to']['lon'])
        self.assertEqual(r.json()['travel']['from']['lat'], travel['travel']['from']['lat'])
        self.assertEqual(r.json()['travel']['from']['lon'], travel['travel']['from']['lon'])

    def test_availability_invalid_accessibility(self):
        """
        Test case for a availability query with invalid accessibility feature.
        Reads the travel properties from test-data.json, sets the
        travel departureTimeEarliest to next monday at 11:00Z.
        Expects response with zero travel availabilities.
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        # Trip availaibility inquiry
        travel = self.testdata['travel_data_accessibility']
        travel["passengers"][0]["accessibility"][0]["title"] = "TESTING_ACCESSIBILITY"
        product = self.testdata['test_products_current_date_response']
        t = datetime.datetime.now()
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=11, minute=00).isoformat()
        r = lippuclient.availability_request(self.envdata['base_url'],
                          headers=headers, payload=travel)
        logging.info("test_availability_invalid_accessibility, availability response: %s"
                     % r.json())
        self.assertEqual(r.status_code, 400)

if __name__ == '__main__':
    unittest.main()
