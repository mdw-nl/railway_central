client_name='testclient'
client_secret='fe95901a-a153-47db-a70c-0aaba4de1cf3'
access_token=$(curl -X POST -u "$client_name:$client_secret" -d "grant_type=client_credentials" https://dcra-keycloak.railway.medicaldataworks.nl/auth/realms/railway/protocol/openid-connect/token | python3 -c "import sys, json; print(json.load(sys.stdin)['access_token'])")
train_id=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/stations?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"'$client_name'"}'  | python3 -c "import sys, json; print(json.load(sys.stdin)['id'])")
echo "add station response train id: $train_id"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"name":"statistics","dockerImageUrl":"registry.gitlab.com/medicaldataworks/railway/prototypetrain:master","ownerId":"03838bb4-8103-4a98-a9c3-d4848b13b3f6","calculationStatus":"REQUESTED", "currentIteration":0}')
echo "add train response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"","stationId":1, "iteration":0, "master": false, "input":"2"}')
echo "add client task 1 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"","stationId":1, "iteration":0, "currentIteration":0, "master": false, "input":"6"}')
echo "add client task 2 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"REQUESTED","result":"","stationId":1, "iteration":0, "master": true, "input":""}')
echo "add master task response: $response"