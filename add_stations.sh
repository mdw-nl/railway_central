timl_name='timl'
timl_secret='65d9ab8f-94ad-4320-af80-d55549de8b62'
timh_name='timh'
johan_name='johan'
ananya_name='ananya'
maastro_name='maastro'

access_token=$(curl -X POST -u "$timl_name:$timl_secret" -d "grant_type=client_credentials" https://dcra-keycloak.railway.medicaldataworks.nl/auth/realms/railway/protocol/openid-connect/token | python3 -c "import sys, json; print(json.load(sys.stdin)['access_token'])")
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'$timl_name'"}')
echo "add station response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'$timh_name'"}')
echo "add station response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'$johan_name'"}')
echo "add station response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'$ananya_name'"}')
echo "add station response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'$maastro_name'"}')
echo "add station response: $response"