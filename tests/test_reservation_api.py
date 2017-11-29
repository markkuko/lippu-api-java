# coding: utf-8

"""
    Lippu

    Transport Code broughts together legal provisions on transport market under one act.
    The LIPPU-project was created to help transport operators enable Mobility as a Service
    (MaaS) capabilities. This is API specification of LIPPU-project.
    NOTE, The ticket payment capabilities are scoped out from LIPPU-project.

"""

import os
import sys
from tests import lippuclient
import datetime
import unittest
import uuid
import zulu
import requests
import json
import logging


class TestReservationApi(unittest.TestCase):
    """ ReservationApi integration tests """

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
        token = lippuclient.get_authentication_token(self.envdata['base_url'],
                                                     str(uuid.uuid4()),
                                                     self.testdata['valid_client1'],
                                                     self.testdata['key_id_client1'],
                                                     self.testdata['key_path_client1'])
        self.headers =  lippuclient.generate_headers(account_id=self.testdata['valid_client1'],
                                                token=token,
                                                language="fi")
    def tearDown(self):
        """
        Tear down test data.
        """
        pass

    def test_reservation_invalid_reservation_ids(self):
        """
        Test case for invalid reservation id, reservation
        request should fail.
        """

        # Trip availaibility inquiry
        reservation = self.testdata['invalid_reservation_id']

        logging.info("test_reservation, request data %s" % reservation)
        r =  lippuclient.reservation_request(self.envdata['base_url'],
                                       headers=self.headers, payload=reservation)
        logging.info("test_reservation, response %s" % r.text)
        self.assertEqual(r.status_code, 404)

    def test_reservation_delete_without_caseid(self):
        """
        Test case for reservation_delete, without case id.

        Cancelletion of transport reservation
        """

        r = lippuclient.reservation_delete(self.envdata['base_url'],
                                  headers=self.headers, reservation_id="")
        logging.info("test_reservation_delete_without_caseid, response: %s"
                     % r.text)
        self.assertEqual(r.status_code, 400)

    def test_reservation_delete_non_valid_caseid(self):
        """
        Test case for reservation_delete, invalid case id.

        Cancelletion of transport reservation
        """

        non_valid_case_id = self.testdata['non_valid_caseid']

        r = lippuclient.reservation_delete(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 reservation_id=non_valid_case_id)
        logging.info("test_reservation_delete_non_valid_caseid, response: %s"
                     % r.text)
        self.assertEqual(r.status_code, 404)

    def test_reservation_delete(self):
        """
        Test case for reservation_delete, create reservation and then
        delete it.

        Cancelletion of transport reservation
        """

        # Add reservation and test you can delete it with the case id
        travel = self.testdata['travel_data']
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                   headers=self.headers, payload=travel)
        logging.info("test_reservation_delete, availability response: %s"
                 % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        reservation = {'reservations': [
            {'travelEntitlementId': r_availability.json()['availability'][0]['travelEntitlementId'],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                      headers=self.headers, payload=reservation)
        logging.info("test_reservation_delete, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        caseId = r_reservation.json()['caseId']
        r_delete = lippuclient.reservation_delete(self.envdata['base_url'],
                                                        headers=self.headers,
                                                        reservation_id=caseId)
        self.assertEqual(r_delete.status_code, 200)

        # Test that already deleted reservation is deleted and could
        # not be deleted again.
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r_delete2 = lippuclient.reservation_delete(self.envdata['base_url'],
                                                         headers=self.headers,
                                                         reservation_id=caseId)
        self.assertEqual(r_delete2.status_code, 404)

    def test_reservation_extra_services(self):
        """
        Test case for reservation with extra services requested.
        """

        # Add reservation and test you can delete it with the case id
        t = datetime.datetime.now()
        travel = self.testdata['travel_data_extra_services']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=14, minute=00).isoformat()
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=self.headers, payload=travel)
        logging.info("test_reservation_extra_services, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        travel_entitlement_id = r_availability.json()['availability'][0]['travelEntitlementId']
        extra_service_id = r_availability.json()['availability'][0]['applicableForPassengers'][0]['extraServices'][0]['extraServiceReservationId']
        reservation = {'reservations': [
            {'travelEntitlementId':travel_entitlement_id ,
             'chosenExtraReservationIds' : [extra_service_id],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=self.headers, payload=reservation)
        logging.info("test_reservation_extra_services, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        self.assertNotEqual(r_reservation.json()['caseId'], None)
        self.assertNotEqual(r_reservation.json()['caseId'], '')
        self.assertNotEqual(r_reservation.json()['confirmedReservations'], None)
        self.assertEqual(r_reservation.json()['confirmedReservations'][0]['travelEntitlementId'], travel_entitlement_id)
        caseId = r_reservation.json()['caseId']

        # Test that reservation has the extra service reserved in
        # the reservation request.
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 travel_entitlement_id=travel_entitlement_id)
        logging.info("test_reservation_extra_services, travel entitlment response %s"
                     % r_reservation.text)
        self.assertEqual(r.json()['travelEntitlementId'], travel_entitlement_id)
        self.assertEqual(r.json()['extraServices'][0]['extraServiceReservationId'], extra_service_id)

    def test_reservation_do_not_include_extra_services(self):
        """
        Test case for reservation with leaving out extra services,
        that were in availability request (soft booked reservation).
        """

        t = datetime.datetime.now()
        travel = self.testdata['travel_data_extra_services']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=14, minute=00).isoformat()
        logging.info("test_reservation_extra_services, availability request: %s"
                     % travel)
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=self.headers, payload=travel)
        logging.info("test_reservation_extra_services, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        travel_entitlement_id = r_availability.json()['availability'][0]['travelEntitlementId']
        extra_service_id = r_availability.json()['availability'][0]['applicableForPassengers'][0]['extraServices'][0]['extraServiceReservationId']
        reservation = {'reservations': [
            {'travelEntitlementId':travel_entitlement_id ,
             'chosenExtraReservationIds' : [],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=self.headers, payload=reservation)
        logging.info("test_reservation_extra_services, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        self.assertNotEqual(r_reservation.json()['caseId'], None)
        self.assertNotEqual(r_reservation.json()['caseId'], '')
        self.assertNotEqual(r_reservation.json()['confirmedReservations'], None)
        self.assertEqual(r_reservation.json()['confirmedReservations'][0]['travelEntitlementId'], travel_entitlement_id)
        caseId = r_reservation.json()['caseId']

        # Test that reservation does not have the extra service reserved in
        # the reservation request.
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 travel_entitlement_id=travel_entitlement_id)
        logging.info("test_reservation_extra_services, travel entitlment response %s"
                     % r_reservation.text)
        self.assertEqual(r.json()['travelEntitlementId'], travel_entitlement_id)
        self.assertEqual(r.json()['extraServices'], [])

    def test_reservation_extra_service_invalid_reservation_id(self):
        """
        Test case for reservation with invalid extra service
        reservation id, reservation request fails.
        """

        t = datetime.datetime.now()
        travel = self.testdata['travel_data_extra_services']
        travel["travel"]["departureTimeEarliest"]  = zulu.now(). \
            shift(days=(7 - t.weekday())).replace(hour=14, minute=00).isoformat()
        logging.info("test_reservation_extra_service_invalid_reservation_id,"
                     "availability request: %s"
                     % travel)
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=self.headers, payload=travel)
        logging.info("test_reservation_extra_service_invalid_reservation_id,"
                     " availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        travel_entitlement_id = r_availability.json()['availability'][0]['travelEntitlementId']
        reservation = {'reservations': [
            {'travelEntitlementId':travel_entitlement_id ,
             'chosenExtraReservationIds' : [str(uuid.uuid4())],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=self.headers, payload=reservation)
        logging.info("test_reservation_extra_service_invalid_reservation_id, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 404)

        # Test that travel entitlement is not confirmed,
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 travel_entitlement_id=travel_entitlement_id)
        logging.info("test_reservation_extra_service_invalid_reservation_id, "
                     "travel entitlement response %s"
                     % r.text)
        self.assertEqual(r.status_code, 404)

    def test_reservation_accessibility(self):
        """
        Test case for reservation with accessibility requirements.
        """

        t = datetime.datetime.now()
        travel = self.testdata['travel_data_accessibility']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=14, minute=00).isoformat()
        logging.info("test_reservation_accessibility, availability request: %s"
                     % travel)
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=self.headers, payload=travel)
        logging.info("test_reservation_accessibility, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        travel_entitlement_id = r_availability.json()['availability'][0]['travelEntitlementId']
        accessibility_id = r_availability.json()['availability'][0]['applicableForPassengers'][0]['accessibility'][0]['accessibilityReservationId']
        reservation = {'reservations': [
            {'travelEntitlementId':travel_entitlement_id ,
             'chosenAccessibilityReservationIds' : [accessibility_id],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=self.headers, payload=reservation)
        logging.info("test_reservation_accessibility, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        self.assertNotEqual(r_reservation.json()['caseId'], None)
        self.assertNotEqual(r_reservation.json()['caseId'], '')
        self.assertNotEqual(r_reservation.json()['confirmedReservations'], None)
        self.assertEqual(r_reservation.json()['confirmedReservations'][0]['travelEntitlementId'], travel_entitlement_id)
        caseId = r_reservation.json()['caseId']

        # Test that reservation has the accessibility reserved in
        # the reservation request is included in the travel entitlement.
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 travel_entitlement_id=travel_entitlement_id)
        logging.info("test_reservation_accessibility, travel entitlement response %s"
                     % r_reservation.text)
        self.assertEqual(r.json()['travelEntitlementId'], travel_entitlement_id)
        self.assertEqual(r.json()['accessibility'][0]['accessibilityReservationId'], accessibility_id)

    def test_reservation_do_not_include_accessibility(self):
        """
        Test case for reservation with leaving out accessibility requirements
        that were in availability request.
        """

        t = datetime.datetime.now()
        travel = self.testdata['travel_data_accessibility']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=14, minute=00).isoformat()
        logging.info("test_reservation_do_not_include_accessibility, availability request: %s"
                     % travel)
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=self.headers, payload=travel)
        logging.info("test_reservation_do_not_include_accessibility, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        travel_entitlement_id = r_availability.json()['availability'][0]['travelEntitlementId']
        accessibility_id = r_availability.json()['availability'][0]['applicableForPassengers'][0]['accessibility'][0]['accessibilityReservationId']
        reservation = {'reservations': [
            {'travelEntitlementId':travel_entitlement_id ,
             'chosenAccessibilityReservationIds' : [],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=self.headers, payload=reservation)
        logging.info("test_reservation_do_not_include_accessibility, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        self.assertNotEqual(r_reservation.json()['caseId'], None)
        self.assertNotEqual(r_reservation.json()['caseId'], '')
        self.assertNotEqual(r_reservation.json()['confirmedReservations'], None)
        self.assertEqual(r_reservation.json()['confirmedReservations'][0]['travelEntitlementId'], travel_entitlement_id)
        caseId = r_reservation.json()['caseId']

        # Test that reservation does not have the accessibility features in
        # the reservation request.
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 travel_entitlement_id=travel_entitlement_id)
        logging.info("test_reservation_do_not_include_accessibility, travel entitlement response %s"
                     % r_reservation.text)
        self.assertEqual(r.json()['travelEntitlementId'], travel_entitlement_id)
        self.assertEqual(r.json()['accessibility'], [])

    def test_reservation_accessibility_invalid_reservation_id(self):
        """
        Test case for reservation with invalid accessibility requirements
        reservation id, reservation request fails.
        """

        t = datetime.datetime.now()
        travel = self.testdata['travel_data_accessibility']
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=14, minute=00).isoformat()
        logging.info("test_reservation_accessibility, availability request: %s"
                     % travel)
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=self.headers, payload=travel)
        logging.info("test_reservation_accessibility, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        travel_entitlement_id = r_availability.json()['availability'][0]['travelEntitlementId']
        reservation = {'reservations': [
            {'travelEntitlementId':travel_entitlement_id ,
             'chosenAccessibilityReservationIds' : [str(uuid.uuid4())],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=self.headers, payload=reservation)
        logging.info("test_reservation_accessibility, reservation response %s"
                     % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 404)

        # Test that reservation does not exits.
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 travel_entitlement_id=travel_entitlement_id)
        logging.info("test_reservation_accessibility, travel entitlement response %s"
                     % r.text)
        self.assertEqual(r.status_code, 404)

    def test_reservation_extra_services_accessibility(self):
        """
        Test case for reservation with extra services and accessibility
        requirements
        """

        t = datetime.datetime.now()
        travel = self.testdata['travel_data_extra_services']
        travel['passengers'][0]['accessibility'] =[{"title":"LOW-FLOOR"}]
        travel["travel"]["departureTimeEarliest"]  = zulu.now().shift(days=(7 - t.weekday())). \
            replace(hour=14, minute=00).isoformat()
        r_availability = lippuclient.availability_request(self.envdata['base_url'],
                                                          headers=self.headers, payload=travel)
        logging.info("test_reservation_extra_services_accessibility, availability response: %s"
                     % r_availability)
        self.assertEqual(r_availability.status_code, 200)
        self.assertGreater(len(r_availability.json()['availability']), 0)

        # Make reservation
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        travel_entitlement_id = r_availability.json()['availability'][0]['travelEntitlementId']
        extra_service_id = r_availability.json()['availability'][0]\
            ['applicableForPassengers'][0]['extraServices'][0]['extraServiceReservationId']
        accessibility_id = r_availability.json()['availability'][0]\
            ['applicableForPassengers'][0]['accessibility'][0]['accessibilityReservationId']
        reservation = {'reservations': [
            {'travelEntitlementId':travel_entitlement_id ,
             'chosenExtraReservationIds' : [extra_service_id],
             'chosenAccessibilityReservationIds' : [accessibility_id],
             'customerInfo': [self.testdata['customer_info']]}]}
        logging.info("Sending reservation %s" % reservation)
        r_reservation = lippuclient.reservation_request(self.envdata['base_url'],
                                                        headers=self.headers, payload=reservation)
        logging.info("test_reservation_extra_services_accessibility, "
                     "reservation response %s" % r_reservation.text)
        self.assertEqual(r_reservation.status_code, 200)
        self.assertNotEqual(r_reservation.json()['caseId'], None)
        self.assertNotEqual(r_reservation.json()['caseId'], '')
        self.assertNotEqual(r_reservation.json()['confirmedReservations'], None)
        self.assertEqual(r_reservation.json()['confirmedReservations'][0]['travelEntitlementId'], travel_entitlement_id)
        caseId = r_reservation.json()['caseId']

        # Test that reservation has the extra service and accessibility
        # requirements reserved in the reservation request.
        self.headers['X-Message-Id'] = str(uuid.uuid4())
        r= lippuclient.travel_entitlement_status(self.envdata['base_url'],
                                                 headers=self.headers,
                                                 travel_entitlement_id=travel_entitlement_id)
        logging.info("test_reservation_extra_services_accessibility, "
                     "travel entitlment response %s"
                     % r_reservation.text)
        self.assertEqual(r.json()['travelEntitlementId'], travel_entitlement_id)
        self.assertEqual(r.json()['extraServices'][0]['extraServiceReservationId'], extra_service_id)
        self.assertEqual(r.json()['accessibility'][0]['accessibilityReservationId'], accessibility_id)


if __name__ == '__main__':
    unittest.main()
