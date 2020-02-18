train_id=$1
timh_name='timh'
timh_secret='4be352c6-dec2-48ec-a424-e69df2832b0e'
access_token=$(curl -X POST -u "$timh_name:$timh_secret" -d "grant_type=client_credentials" https://dcra-keycloak.railway.medicaldataworks.nl/auth/realms/railway/protocol/openid-connect/token | python3 -c "import sys, json; print(json.load(sys.stdin)['access_token'])")

response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"{\"calculation_result\": 6}","stationId":1, "iteration":0, "currentIteration":0, "master": false, "input":""}')
echo "add client task 2 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"{\"calculation_result\": 10}","stationId":2, "iteration":0, "currentIteration":0, "master": false, "input":""}')
echo "add client task 2 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"REQUESTED","result":"","stationId":2, "iteration":0, "master": true, "input":"{\"iterations\": 5}"}')
echo "add master task response: $response"