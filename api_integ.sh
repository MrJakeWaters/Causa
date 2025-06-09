curl -X POST -H 'Content-Type: application/json' -d @decision.json http://localhost:8080/create_decision | jq .
curl http://localhost:8080/get_decisions | jq .
