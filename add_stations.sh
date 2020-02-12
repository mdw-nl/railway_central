timl_name='timl'
timl_secret='aa77b2d1-f7f9-41a8-a5a8-98de32aa9d94'
timh_name='timh'
timh_secret='5b2a6ee7-b9c4-4922-ad17-846258c7491e'
johan_name='johan'
johan_secret='d991806c-c948-4947-b641-6b65e9c4087b'
ananya_name='ananya'
ananya_secret='48996987-604f-4604-a462-01db2e16b925'

access_token=$(curl -X POST -u "$timl_name:$timl_secret" -d "grant_type=client_credentials" https://dcra-keycloak.railway.medicaldataworks.nl/auth/realms/railway/protocol/openid-connect/token | python3 -c "import sys, json; print(json.load(sys.stdin)['access_token'])")
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'timl_name'"}')
echo "add station response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'timh_name'"}')
echo "add station response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'johan_name'"}')
echo "add station response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'ananya_name'"}')
echo "add station response: $response"