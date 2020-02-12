train_id=$1
timl_name='timl'
timl_secret='aa77b2d1-f7f9-41a8-a5a8-98de32aa9d94'
access_token=$(curl -X POST -u "$timl_name:$timl_secret" -d "grant_type=client_credentials" https://dcra-keycloak.railway.medicaldataworks.nl/auth/realms/railway/protocol/openid-connect/token | python3 -c "import sys, json; print(json.load(sys.stdin)['access_token'])")

response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"","stationId":1, "iteration":0, "master": false, "input":"2"}')
echo "add client task 1 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"","stationId":2, "iteration":0, "currentIteration":0, "master": false, "input":"6"}')
echo "add client task 2 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"","stationId":3, "iteration":0, "currentIteration":0, "master": false, "input":"7"}')
echo "add client task 2 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"COMPLETED","result":"","stationId":4, "iteration":0, "currentIteration":0, "master": false, "input":"8"}')
echo "add client task 2 response: $response"
response=$(curl --verbose --location --request POST https://dcra.railway.medicaldataworks.nl/api/trains/$train_id/tasks?access_token=$access_token --header 'Content-Type: application/json' --data-raw '{"creationTimestamp":"2019-07-02T04:14:00.742+0000","calculationStatus":"REQUESTED","result":"","stationId":1, "iteration":0, "master": true, "input":""}')
echo "add master task response: $response"