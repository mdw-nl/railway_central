timh_name='timh'
timh_secret='b70c3982-cad2-43c8-8eef-14391aa373e6'
access_token=$(curl -X POST -u "$timh_name:$timh_secret" -d "grant_type=client_credentials" https://dcra-keycloak.railway.medicaldataworks.nl/auth/realms/railway/protocol/openid-connect/token | python3 -c "import sys, json; print(json.load(sys.stdin)['access_token'])")

train_id=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"statistics","dockerImageUrl":"registry.gitlab.com/medicaldataworks/railway/prototypetrain:master","ownerId":"03838bb4-8103-4a98-a9c3-d4848b13b3f6","calculationStatus":"REQUESTED", "currentIteration":0}' | python3 -c "import sys, json; print(json.load(sys.stdin)['id'])")
echo "add train response id: $train_id"