# coding: utf-8

"""
    Lippu

    Transport Code broughts together legal provisions on transport market under one act.
    The LIPPU-project was created to help transport operators enable Mobility as a Service
    (MaaS) capabilities. This is API specification of LIPPU-project.
    NOTE, The ticket payment capabilities are scoped out from LIPPU-project.

    OpenAPI spec version: 1.0.0

"""

import os
import sys
from tests import lippuclient
import unittest
import uuid
import requests
import json
import zulu
import logging


class TestTravelentitlementAPI(unittest.TestCase):
    """ TestTravelentitlementAPI unit test stubs """

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
        logging.debug("TestTravelentitlementAPI: Setting target environment %s", target_environment)
        self.envdata = json.load(env_json)[target_environment]
        env_json.close()

    def tearDown(self):
        """
        Tear down test data.
        """
        pass

    def test_activate_travel_entitlement(self):
        """
        Tests the whole reservation flow,
        availability query and actual reservation. Then deletes
        the reservation.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")

        # Make reservation
        r_reservation = self.make_reservation(headers)
        # Travel entitlement status
        headers['X-Message-Id'] = str(uuid.uuid4())
        case_id = r_reservation.json()['caseId']
        entitlement_id = r_reservation.json()['confirmedReservations'][0]['travelEntitlementId']
        r_status = lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                  headers=headers,
                                                  travel_entitlement_id=entitlement_id)
        logging.info("test_activate_travel_entitlement, status for %s, response: %s"
                     %(entitlement_id, r_status.text))
        self.assertEqual(r_status.status_code, 200)
        self.assertEqual(r_status.json()['activated'], False)

        # Travel entitlement activation
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_active = lippuclient.travel_entitlement_activate(self.envdata['base_url'],
                                                        headers=headers,
                                                        travel_entitlement_id=entitlement_id)
        logging.info("test_activate_travel_entitlement, activation for %s, response: %s"
                     %(entitlement_id, r_active.text))
        self.assertEqual(r_active.status_code, 200)
        self.assertEqual(r_active.json()['activated'], True)

        # Delete the the travel entitlement id
        headers['X-Message-Id'] = str(uuid.uuid4())
        case_id = r_reservation.json()['caseId']
        r_delete = lippuclient.travel_entitlement_delete(self.envdata['base_url'],
                                                  headers=headers,
                                                  travel_entitlement_id=entitlement_id)
        logging.info("test_activate_travel_entitlement, delete for %s, response: %s"
                     %(entitlement_id, r_delete.text))
        self.assertEqual(r_delete.status_code, 200)

        # Travel entitlement status, already deleted
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_status = lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                        headers=headers,
                                                        travel_entitlement_id=entitlement_id)
        logging.info("test_activate_travel_entitlement, reservation delete for %s, response: %s"
                     %(entitlement_id, r_status.text))
        self.assertEqual(r_status.status_code, 404)

        # Reservation had only one entitlement, it should be also
        # deleted.
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_reservation_delete = lippuclient.reservation_delete(self.envdata['base_url'],
                                                         headers=headers,
                                                         reservation_id=case_id)
        logging.info("test_activate_travel_entitlement, reservation delete for %s, response: %s"
                     %(entitlement_id, r_status.text))
        self.assertEqual(r_status.status_code, 404)


    def test_delete_reservation_travel_entitlement(self):
        """
        Tests that deleting reservation also removes
        the travel entitlement associated with reservation.

        """
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")

        # Make reservation
        r_reservation = self.make_reservation(headers)
        # Travel entitlement status
        headers['X-Message-Id'] = str(uuid.uuid4())
        case_id = r_reservation.json()['caseId']
        entitlement_id = r_reservation.json()['confirmedReservations'][0]['travelEntitlementId']
        r_status = lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                         headers=headers,
                                                         travel_entitlement_id=entitlement_id)
        logging.info("test_activate_travel_entitlement, status for %s, response: %s"
                     %(entitlement_id, r_status.text))
        self.assertEqual(r_status.status_code, 200)
        self.assertEqual(r_status.json()['activated'], False)

        # Travel entitlement status
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_reservation_delete = lippuclient.reservation_delete(self.envdata['base_url'],
                                                              headers=headers,
                                                              reservation_id=case_id)
        logging.info("test_activate_travel_entitlement, reservation delete for %s, response: %s"
                     %(entitlement_id, r_status.text))
        self.assertEqual(r_status.status_code, 200)


        # Travel entitlement status
        headers['X-Message-Id'] = str(uuid.uuid4())
        r_status = lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                         headers=headers,
                                                         travel_entitlement_id=entitlement_id)
        logging.info("test_activate_travel_entitlement, status for %s, response: %s"
                     %(entitlement_id, r_status.text))
        self.assertEqual(r_status.status_code, 404)

    def test_delete_travel_entitlement_non_valid_id(self):
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")
        r= lippuclient.travel_entitlement_delete(self.envdata['base_url'],
                                                         headers=headers,
                                                         travel_entitlement_id=str(uuid.uuid4()))
        self.assertEqual(r.status_code, 404)

    def test_activate_travel_entitlement_non_valid_id(self):
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")
        r= lippuclient.travel_entitlement_activate(self.envdata['base_url'],
                                                 headers=headers,
                                                 travel_entitlement_id=str(uuid.uuid4()))
        self.assertEqual(r.status_code, 404)

    def test_status_travel_entitlement_non_valid_id(self):
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=headers,
                                                 travel_entitlement_id=str(uuid.uuid4()))
        self.assertEqual(r.status_code, 404)

    def test_delete_travel_entitlement_expired_token(self):
        token = self.testdata['expired_token']
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")
        r= lippuclient.travel_entitlement_delete(self.envdata['base_url'],
                                                 headers=headers,
                                                 travel_entitlement_id=str(uuid.uuid4()))
        self.assertEqual(r.status_code, 403)

    def test_activate_travel_entitlement_expired_token(self):
        token = self.testdata['expired_token']
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")
        r= lippuclient.travel_entitlement_activate(self.envdata['base_url'],
                                                   headers=headers,
                                                   travel_entitlement_id=str(uuid.uuid4()))
        self.assertEqual(r.status_code, 403)

    def test_status_travel_entitlement_expired_token(self):
        token = self.testdata['expired_token']
        headers = lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                               token=token,
                                               language="fi")
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=headers,
                                                 travel_entitlement_id=str(uuid.uuid4()))
        self.assertEqual(r.status_code, 403)

    def make_reservation(self, headers):
        """
        Helper funtion to make reservation.

        :param headers: HTTP headers for requests, must have a valid access token.
        :return: Response from reservation request.
        """
        travel = self.testdata['travel_data']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=headers, payload=travel)
        logging.info("make_reservation, availability response: %s"
                     % r_availability)
        # Make reservation
        headers['X-Message-Id'] = str(uuid.uuid4())
        reservation = {'reservations': []}
        for a in r_availability.json()['availability']:
            reservation['reservations'].append({'travelEntitlementId': a['travelEntitlementId'],
                                                'customerInfo': [{'name': 'Matti','phone': 'adsf', 'email': 'asdf'}]})
        logging.info("Sending reservation request %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=headers, payload=reservation)
        return r_reservation
if __name__ == '__main__':
    unittest.main()
