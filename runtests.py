# coding: utf-8
"""
Lippu-project test suite runner.
"""

import unittest
import logging
import argparse
from tests import test_login_api, test_availability_api, test_reservation_api, test_wholechain, test_products_api
#, test_availability_api, kk


if __name__ == '__main__':

    parser = argparse.ArgumentParser(description='LIPPU-project testsuite.')
    parser.add_argument('--log','-l', help='Set log level, default WARNING. Set to INFO,'
                                           'if you want outputs from requests '
                                           'and DEBUG for full debug messages',
                        default='WARNING')
    args = parser.parse_args()
    log_arg = args.log.upper()
    numeric_level = getattr(logging, log_arg, None)
    if (log_arg is not None and log_arg != "") and not isinstance(numeric_level, int):
        raise ValueError('Invalid log level: %s' % args.log)
    logging.basicConfig(level=numeric_level)
    logging.info("Starting LIPPU-project integration tests")
    # Define test classes to run
    test_classes_to_run = [
                           test_login_api.TestLoginApi,
                           test_products_api.TestProductsApi,
                           test_availability_api.TestAvailabilityApi,
                           test_reservation_api.TestReservationApi,
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
    logging.info("LIPPU-project integration tests finished")
