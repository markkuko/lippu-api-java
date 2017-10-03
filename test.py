# coding: utf-8
"""
Lippu-project test suite runner.
"""
from __future__ import print_function

import unittest
import logging
import argparse
from tests import test_login_api, test_availability_api, test_reservation_api, test_wholechain
#, test_availability_api, kk


if __name__ == '__main__':

    parser = argparse.ArgumentParser(description='LIPPU-project testsuite.')
    parser.add_argument('--log','-l', help='Set log level, default WARNING. Set to INFO,'
                                           'if you want outputs from requests '
                                           'and DEBUG for full debug messages',
                        default='WARNING')
    args = parser.parse_args()

    numeric_level = getattr(logging, args.log.upper(), None)
    if not isinstance(numeric_level, int):
        raise ValueError('Invalid log level: %s' % args.log)
    logging.basicConfig(level=numeric_level)
    logging.info("Starting LIPPU-project tests")
    # Define test classes to run
    test_classes_to_run = [
                           test_reservation_api.TestReservationApi,
                           test_login_api.TestLoginApi,
                           test_availability_api.TestAvailabilityApi,
                           test_wholechain.TestWholeChain
                           ]

    loader = unittest.TestLoader()

    suites_list = []
    for test_class in test_classes_to_run:
        suite = loader.loadTestsFromTestCase(test_class)
        suites_list.append(suite)

    big_suite = unittest.TestSuite(suites_list)

    runner = unittest.TextTestRunner()
    results = runner.run(big_suite)
    logging.info("LIPPU-project tests finished")
