#!/bin/bash

# Check that openssl command exists in the path.
if ! [ -x "$(command -v openssl)" ];
then
  echo 'Error: openssl is not installed.' >&2
  exit 1
fi
# Check that keys-directory exists
if [ ! -d "keys" ];
then
    echo "Keys directory does not exists, creating.."
    mkdir keys
fi

# Check that operator.pem-file exits.
if [ ! -f "keys/operator.pem" ];
then
    echo "Operator PEM does not exist, creating.."
    openssl genrsa -out operator.pem 2048
fi

# Check that server side public key exists.
if [ ! -f "keys/operator.pub" ];
then
    echo "Operator public key does not exist, creating.."
    openssl rsa -in operator.pem -pubout > keys/operator.pub

fi

# Check that server side private key exists.
if [ ! -f "keys/operator.key" ];
then
    echo "Operator private key does not exist, creating.."
    openssl pkcs8 -topk8 -inform PEM -outform DER -in operator.pem  -nocrypt > keys/operator.key
fi

# Run the spring boot server.
./gradlew clean bootRun
