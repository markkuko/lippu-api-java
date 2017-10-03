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


class TestWholeChain(unittest.TestCase):
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

    def test_make_reservation_and_delete(self):

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
        # Make reservation
        headers['X-Message-Id'] = str(uuid.uuid4())
        reservation = {'reservations': [
            {'reservationData': r_availability.json()['availability'][0]['reservationData'],
             'customerInfo': [{'name': 'Matti','phone': 'adsf', 'email': 'asdf'}]}]}
        logging.info("Sending reservation %s" % (reservation))
        r_reservation = requests.post(self.envdata['reservation_url'],
                                       headers=headers, json=reservation)
        logging.info("test_make_reservation_and_delete, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        # Delete resevation
        headers['X-Message-Id'] = str(uuid.uuid4())
        caseId = r_reservation.json()['caseId']
        r_delete = requests.delete(self.envdata['reservation_url']+ '/'
                                         + caseId ,
                                         headers=headers)
        logging.info("test_make_reservation_and_delete, reservation delete for %s, response: %s"
                      %(caseId, r_delete.text))
        self.assertEqual(r_delete.status_code, 200)


if __name__ == '__main__':
    unittest.main()
