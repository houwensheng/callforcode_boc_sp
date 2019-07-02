import urllib3, requests, json

iam_token = "eyJraWQiOiIyMDE5MDIwNCIsImFsZyI6IlJTMjU2In0.eyJpYW1faWQiOiJpYW0tU2VydmljZUlkLTA0NWUzNGNkLWFkMDgtNDc5Ni05MDFlLWIzMWI2NjEzNjkzZiIsImlkIjoiaWFtLVNlcnZpY2VJZC0wNDVlMzRjZC1hZDA4LTQ3OTYtOTAxZS1iMzFiNjYxMzY5M2YiLCJyZWFsbWlkIjoiaWFtIiwiaWRlbnRpZmllciI6IlNlcnZpY2VJZC0wNDVlMzRjZC1hZDA4LTQ3OTYtOTAxZS1iMzFiNjYxMzY5M2YiLCJzdWIiOiJTZXJ2aWNlSWQtMDQ1ZTM0Y2QtYWQwOC00Nzk2LTkwMWUtYjMxYjY2MTM2OTNmIiwic3ViX3R5cGUiOiJTZXJ2aWNlSWQiLCJhY2NvdW50Ijp7InZhbGlkIjp0cnVlLCJic3MiOiI0OTU0MDI0ZTE5NDY0MTE4ODQ5M2M2ZWRiOGMwYWE4MyJ9LCJpYXQiOjE1NjEyOTY1MTksImV4cCI6MTU2MTMwMDExOSwiaXNzIjoiaHR0cHM6Ly9pYW0ubmcuYmx1ZW1peC5uZXQvb2lkYy90b2tlbiIsImdyYW50X3R5cGUiOiJ1cm46aWJtOnBhcmFtczpvYXV0aDpncmFudC10eXBlOmFwaWtleSIsInNjb3BlIjoiaWJtIG9wZW5pZCIsImNsaWVudF9pZCI6ImJ4IiwiYWNyIjoxLCJhbXIiOlsicHdkIl19.eFMchW4SYj-sh0MPBqIFCMPfuIHgHk_Fq7I9BJkbVlQohAkJlbmot2MaDKTfNEQqpwPDaXcUo_HtdATaNvakwwsdpRqOLFFsF5z2YU_7qIEvdXvdvkbMQVAebAl3rMMUjovJffqh0-1YHjFwJF_1bjv3l5uOZrsG_A2-e2hbJ-RadMuC1x-dMzTZ4Ktkp1jVDfKvyc1nBCpnryW-lP-7wewsAWbBxT4kOhhOvCRhtZR_4-NMvfAzYpB3FtZ12BXEWLEJ1-JK1wkgTlkHY28fRL2MuGtnGxBQKHrQEkRp5Awqrb5PmW0BAeqN0evPB93mxRB5Qi5Prb92Ya3wgcd4jA"
ml_instance_id = "6b50131c-e7e8-4e49-b836-93f2300392d9"

asset_val = 20000
transfreq_val = 20
credit_val = 95

# NOTE: generate iam_token and retrieve ml_instance_id based on provided documentation	
header = {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + iam_token, 'ML-Instance-ID': ml_instance_id}

# NOTE: manually define and pass the array(s) of values to be scored in the next line
payload_scoring = {"fields": ["asset", "transfreq", "credit"], "values": [[asset_val, transfreq_val,credit_val]]}

response_scoring = requests.post('https://us-south.ml.cloud.ibm.com/v3/wml_instances/6b50131c-e7e8-4e49-b836-93f2300392d9/deployments/c2409c20-653e-4937-8ad0-d187d28a078a/online', json=payload_scoring, headers=header)
print("Scoring response")
print(json.loads(response_scoring.text))