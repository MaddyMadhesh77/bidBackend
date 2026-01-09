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

