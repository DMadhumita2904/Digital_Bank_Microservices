from fastapi import FastAPI
from pydantic import BaseModel
import joblib
import numpy as np

app = FastAPI(title="Credit Scoring Service", version="1.0")

# Load model
artifact = joblib.load("credit_model.pkl")
model = artifact["model"]
features = artifact["features"]

# Request schema
class CreditRequest(BaseModel):
    average_balance: float
    total_transactions: int
    failed_transaction_rate: float
    total_transferred_amount: float
    incoming_amount: float

@app.post("/predict")
def predict_credit_score(data: CreditRequest):
    X = np.array([[ 
        data.average_balance,
        data.total_transactions,
        data.failed_transaction_rate,
        data.total_transferred_amount,
        data.incoming_amount
    ]])
    
    score = float(model.predict(X)[0])
    
    if score >= 750:
        grade = "Excellent"
    elif score >= 700:
        grade = "Good"
    elif score >= 600:
        grade = "Fair"
    else:
        grade = "Poor"

    return {
        "predicted_credit_score": round(score, 2),
        "grade": grade
    }
