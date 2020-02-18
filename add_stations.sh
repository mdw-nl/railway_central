timl_name='timl'
timh_secret='4be352c6-dec2-48ec-a424-e69df2832b0e'
timh_name='timh'
johan_name='johan'
ananya_name='ananya'
maastro_name='maastro'

access_token=$(curl -X POST -u "$timh_name:$timh_secret" -d "grant_type=client_credentials" https://dcra-keycloak.railway.medicaldataworks.nl/auth/realms/railway/protocol/openid-connect/token | python3 -c "import sys, json; print(json.load(sys.stdin)['access_token'])")
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