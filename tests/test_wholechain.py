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
import zulu
from pprint import pprint
import requests
import logging
from tests import lippuclient


class TestWholeChain(unittest.TestCase):
    """ Test the complete reservation request chain"""

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
        """
        Tear down test data
        """
        pass

    def test_make_reservation_and_delete(self):
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

        # Trip availaibility inquiry
        travel = self.testdata['travel_data']
        travel["travel"]["dateTime"]  = zulu.now().shift(days=2). \
            replace(hour=14, minute=00).isoformat()
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                  headers=headers, payload=travel)
        logging.info("test_make_reservation_and_delete, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)
        self.assertGreater(len(r_availability.json()['availability']), 0)
        # Make reservation
        headers['X-Message-Id'] = str(uuid.uuid4())
        reservation = {'reservations': []}
        for a in r_availability.json()['availability']:
            reservation['reservations'].append({'reservationData': a['reservationData'],
             'customerInfo': [{'name': 'Matti','phone': 'adsf', 'email': 'asdf'}]})
        logging.info("Sending reservation request %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                       headers=headers, payload=reservation)
        logging.info("test_make_reservation_and_delete, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        # Delete the resevation
        headers['X-Message-Id'] = str(uuid.uuid4())
        case_id = r_reservation.json()['caseId']
        r_delete = lippuclient.reservation_delete(self.envdata['base_url'],
                                                        headers=headers,
                                                        reservation_id=case_id)
        logging.info("test_make_reservation_and_delete, reservation delete for %s, response: %s"
                      %(case_id, r_delete.text))
        self.assertEqual(r_delete.status_code, 200)


if __name__ == '__main__':
    unittest.main()
