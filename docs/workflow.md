# Service Request Workflow

## Service Request Workflow
The system processes various types of port service requests, from vessel berthing to cargo handling.

## State Transitions
1. **DRAFT**: A request is created but not yet finalized.
2. **SUBMITTED**: The customer officially requests the service.
3. **VERIFIED**: Operations/Admin verify the request for completeness.
4. **APPROVED/REJECTED**: Management approves or rejects the request based on capacity or compliance.
5. **IN_PROGRESS**: The actual physical operation is currently underway.
6. **COMPLETED**: The service is fully delivered and ready for billing.
7. **CANCELLED**: The request was aborted before execution.

## Role Permissions Matrix

| State       | CUSTOMER | VERIFIER | APPROVER | OPERATOR | FINANCE |
|-------------|----------|----------|----------|----------|---------|
| DRAFT       | R, U, D  | -        | -        | -        | -       |
| SUBMITTED   | R        | R, U     | R        | -        | -       |
| VERIFIED    | R        | R        | R, U     | -        | -       |
| APPROVED    | R        | R        | R        | R, U     | R       |
| IN_PROGRESS | R        | R        | R        | R, U     | R       |
| COMPLETED   | R        | R        | R        | R        | R, U    |
| CANCELLED   | R        | R        | R        | R        | R       |

*(R = Read, U = Update/Action, D = Delete)*

## API Flow Examples
- `POST /api/v1/requests` -> Creates a new request (DRAFT state).
- `PUT /api/v1/requests/{id}/submit` -> Submits the request.
- `PUT /api/v1/requests/{id}/verify` -> Verifies the request.
- `PUT /api/v1/requests/{id}/approve` -> Approves the request.
- `PUT /api/v1/requests/{id}/complete` -> Completes the operation.
