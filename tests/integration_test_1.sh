# add patients
TestNone=$(curl -d "family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333" -X POST http://localhost:8081/patient/add)
TestBorderline=$(curl -d "family=TestBorderline&given=Test&dob=1945-06-24&sex=M&address=2 High St&phone=200-333-4444" -X POST http://localhost:8081/patient/add)
TestInDanger=$(curl -d "family=TestInDanger&given=Test&dob=2004-06-18&sex=M&address=3 Club Road&phone=300-444-5555" -X POST http://localhost:8081/patient/add)
TestEarlyOnset=$(curl -d "family=TestEarlyOnset&given=Test&dob=2002-06-28&sex=F&address=4 Valley Dr&phone=400-555-6666" -X POST http://localhost:8081/patient/add)

# add notes
curl -d "patId=$TestNone&e=Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestBorderline&e=Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late" -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestBorderline&e=Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic" -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestInDanger&e=Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker " -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestInDanger&e=Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high" -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestEarlyOnset&e=Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication" -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestEarlyOnset&e=Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are experiencing back pain when seated for a long time" -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestEarlyOnset&e=Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are a short term Smoker Hemoglobin A1C above recommended level" -X POST http://localhost:8082/patHistory/add
curl -d "patId=$TestEarlyOnset&e=Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction" -X POST http://localhost:8082/patHistory/add

# assess
echo ""
echo "Patient TestNone : $TestNone"
curl -d "patientId=$TestNone" -X POST http://localhost:8080/assess/id

echo ""
echo "Patient TestBorderline : $TestBorderline"
curl -d "patientId=$TestBorderline" -X POST http://localhost:8080/assess/id

echo ""
echo "Patient TestInDanger : $TestInDanger"
curl -d "patientId=$TestInDanger" -X POST http://localhost:8080/assess/id

echo ""
echo "Patient TestEarlyOnset : $TestEarlyOnset"
curl -d "patientId=$TestEarlyOnset" -X POST http://localhost:8080/assess/id