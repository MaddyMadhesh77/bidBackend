# bidBackend

Architecture :

📄 Project Architecture Document (Template)
1. System Overview
   Actors

Admin: Creates products and controls auction lifecycle (start/end).

Bidder: Participates in auctions by placing bids on active auctions.

Core Use-Cases

Admin creates a product.

Admin starts an auction for a product.

Bidder places bids on active auctions.

System updates the highest bid in real time.

Auction ends and the winner is declared.

Non-Functional Requirements

Real-time bid updates.

Strong data consistency for bids.

Secure authentication and role-based access.

2. Data Model & Database Design
   Entities

User
Represents system users (both Admin and Bidder).

Product
Represents an item available for auction.

Auction
Manages the auction lifecycle (created, active, ended).

Bid
Represents an individual bid placed by a bidder.

Order
Stores the final result of an auction, including the winner.

Relationships

User (1) → (N) Bid
A user can place multiple bids.

Auction (1) → (N) Bid
Multiple bids belong to a single auction.

Product (1) → (1) Auction
Each product is auctioned once.

Auction (1) → (0 or 1) Order
An order is created only after the auction ends.

Database Choice (Example)

MongoDB

High write throughput for frequent bids.

Flexible schema for auction data.

Easier horizontal scaling for real-time systems.

3. Backend Architecture
   Layered Design

Controller

Accepts HTTP requests.

Performs request validation.

Delegates processing to services.

Service

Contains business logic.

Validates bids (auction active, bid amount higher than current).

Handles auction rules.

Repository

Handles database operations.

Abstracts database logic from services.

Model

Represents domain entities (User, Product, Auction, Bid).

Maps business concepts to database structure.

DTO (Data Transfer Object)

Controls data exchanged between client and server.

Prevents exposure of internal models.

WebSocket Layer

Pushes real-time bid updates to connected clients.

Example API
POST /bids
- Called by: Bidder
- Validations:
    - Auction must be active
    - Bid amount must be greater than current highest bid
- Flow:
  Controller → Service → Repository → WebSocket notify

4. Frontend (UI) Architecture
   Pages

Login Page – User authentication.

Auction List Page – Displays all active auctions.

Auction Detail Page – Shows auction details and live bids.

Admin Dashboard – Admin controls.

Create Product Page (Admin).

Reusable Components

BidForm – Accepts bid input from bidder.

ProductCard – Displays product/auction summary.

LiveBidList – Displays real-time bid updates.

State Management

Global State

Logged-in user information.

Active auction data.

Local State

Bid input value.

Real-Time Updates

Auction Detail Page subscribes to WebSocket for live bid updates.

5. Testing Strategy
   Unit Tests

Bid validation logic in the Service layer.

Integration Tests

Place bid → store in database → update highest bid.

UI Tests

Verify bid submission and real-time update flow.

6. Future Improvements

Payment integration for winning bids.

Auction analytics and history.

Cloud deployment with monitoring and logging.

Improved security and rate limiting.

API's:

1. POST /auth/login
   Called by: * Any User (Admin or Bidder)

Purpose: * Authenticates a user and provides a session token (JWT) for future requests.

Input: * email

password

Output (Success): * token

userId

role (e.g., "admin" or "bidder")

message

Possible Errors: * Invalid credentials

User not found

2. POST /auth/register (Optional)
   Called by: * New User

Purpose: * Creates a new account in the system.

Input: * username

email

password

role (default: "bidder")

Output (Success): * userId

message

Possible Errors: * Email already exists

Weak password

3. POST /products
   Called by: * Admin

Purpose: * Adds a new product to the inventory that can later be put up for auction.

Input: * name

description

basePrice

imageUrl

Output (Success): * productId

message

Possible Errors: * Unauthorized (Non-admin access)

Invalid product data (e.g., negative base price)

4. API: POST /bids

Called by:
- Bidder

Purpose:
- Allows a bidder to place a bid on an active auction

Input:
- auctionId
- bidAmount

Output (Success):
- bidId
- updatedHighestBid
- message

Possible Errors:
- Auction not active
- Bid amount less than current highest bid
- User not authenticated

5API: POST /auctions/start

Called by:
- Admin

Purpose:
- Starts an auction for a product

Input:
- productId
- startTime
- endTime

Output (Success):
- auctionId
- status (ACTIVE)

Possible Errors:
- Product not found
- Auction already exists
- User not authorized

6. API: POST /auctions/end

Called by:
- Admin

Purpose:
- Ends an active auction and declares winner

Input:
- auctionId

Output (Success):
- winnerUserId
- winningBidAmount
- message

Possible Errors:
- Auction not active
- Auction not found