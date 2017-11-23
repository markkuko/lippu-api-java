# coding: utf-8

"""
    Lippu

    Transport Code broughts together legal provisions on transport market under one act.
    The LIPPU-project was created to help transport operators enable Mobility as a Service
    (MaaS) capabilities. This is API specification of LIPPU-project.
    NOTE, The ticket payment capabilities are scoped out from LIPPU-project.

    OpenAPI spec version: 0.1

"""

import os
import sys
from tests import lippuclient
import unittest
import uuid
import requests
import json
import logging


class TestReservationApi(unittest.TestCase):
    """ ReservationApi unit test stubs """

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
        logging.debug("TestReservationApi: Setting target environment %s", target_environment)
        self.envdata = json.load(env_json)[target_environment]
        env_json.close()

    def tearDown(self):
        """
        Tear down test data.
        """
        pass

    def test_reservation_(self):
        """
        Test case for reservation

        Travel reservation
        """

        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])
        # Trip availaibility inquiry
        reservation = self.testdata['non_valid_reservation_data']
        headers =  lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                            token=token,
                                                            language="fi")
        logging.info("test_reservation, request data %s" % reservation)
        r =  lippuclient.reservation_request(self.envdata['base_url'],
                                       headers=headers, payload=reservation)
        logging.info("test_reservation, response %s" % r.text)
        self.assertEqual(r.status_code, 200)
        self.assertNotEqual(r.json()['caseId'], None)
        self.assertEqual(r.json()['confirmedReservations'], None)

    def test_reservation_delete_without_caseid(self):
        """
        Test case for reservation_delete

        Cancelletion of transport reservation
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])

        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                           token=token,
                                                           language="fi")

        r = lippuclient.reservation_delete(self.envdata['base_url'],
                                  headers=headers, reservation_id="")
        logging.info("test_reservation_delete_without_caseid, response: %s"
                     % r.text)
        self.assertEqual(r.status_code, 400)


    def test_reservation_delete_non_valid_caseid(self):
        """
        Test case for reservation_delete

        Cancelletion of transport reservation
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])

        non_valid_case_id = self.testdata['non_valid_caseid']
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        r = lippuclient.reservation_delete(self.envdata['base_url'],
                                                 headers=headers,
                                                 reservation_id=non_valid_case_id)
        logging.info("test_reservation_delete_non_valid_caseid, response: %s"
                     % r.text)
        self.assertEqual(r.status_code, 404)

    def test_reservation_delete(self):
        """
        Test case for reservation_delete

        Cancelletion of transport reservation
        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                           str(uuid.uuid4()),
                                                           self.testdata['valid_client1'],
                                                           self.testdata['key_id_client1'],
                                                           self.testdata['key_path_client1'])

        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                     token=token,
                                                     language="fi")

        # Add reservation and test you can delete it with the case id
        travel = self.testdata['travel_data']
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                   headers=headers, payload=travel)
        logging.info("test_reservation_delete, availability response: %s"
                 % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        headers['X-Message-Id'] = str(uuid.uuid4())
        reservation = {'reservations': [
            {'travelEntitlementId': r_availability.json()['availability'][0]['travelEntitlementId'],
             'customerInfo': [{'name': 'Matti','phone': 'adsf', 'email': 'asdf'}]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                      headers=headers, payload=reservation)
        logging.info("test_reservation_delete, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        headers['X-Message-Id'] = str(uuid.uuid4())
        caseId = r_reservation.json()['caseId']
        r_delete = lippuclient.reservation_delete(self.envdata['base_url'],
                                                        headers=headers,
                                                        reservation_id=caseId)
        self.assertEqual(r_delete.status_code, 200)

        # Test that already deleted reservation is deleted
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_delete2 = lippuclient.reservation_delete(self.envdata['base_url'],
                                                         headers=headers,
                                                         reservation_id=caseId)
        self.assertEqual(r_delete2.status_code, 404)


if __name__ == '__main__':
    unittest.main()
