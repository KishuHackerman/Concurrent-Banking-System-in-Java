# COMP 346 / Operating Systems - PA2  
## Concurrent Banking System in Java

## Overview
This project simulates a **client-server banking system** in Java using multiple threads.  
A sending client reads transactions from a file and sends them through a network buffer. Two concurrent server threads process the transactions on shared bank accounts, and a receiving client prints the updated transaction results.

The goal of this assignment is to demonstrate:
- **race conditions**
- **thread synchronization**
- **mutual exclusion**
- **busy waiting**
- **semaphore-based producer-consumer coordination**

---

## Project Objective
This assignment is divided into three execution modes:

### Run 1 - Unsynchronized
The shared accounts are accessed without synchronization.  
This causes **race conditions** when the two server threads update the same account at the same time, which may produce inconsistent balances.

### Run 2 - Synchronized
The shared accounts are protected using synchronization.  
This prevents both server threads from modifying the same shared account simultaneously and fixes the race condition.

### Run 3 - Semaphore-Based
The shared accounts remain synchronized, and the **network buffer busy-waiting** is replaced with **semaphores**.  
This improves buffer coordination by blocking threads when buffers are full or empty instead of repeatedly checking in a loop.

---

## Files
### `OSpa2driver.java`
Main driver class.  
Responsible for:
- selecting the execution mode using `RUN_MODE`
- creating all threads
- starting the client, server, and network threads
- waiting for them to finish using `join()`

### `Accounts.java`
Represents a bank account.  
Stores:
- account number
- account type
- first name
- last name
- balance

### `Transactions.java`
Represents one transaction.  
Stores:
- account number
- operation type
- transaction amount
- resulting balance
- transaction error
- transaction status

### `Client.java`
Represents the client side of the system.  
There are two client threads:
- **sending client**: reads transactions from `transaction2.txt` and sends them to the network
- **receiving client**: receives processed transactions from the network and prints them

### `Server.java`
Represents the server side of the system.  
Two concurrent server threads:
- receive transactions from the network
- find the correct account
- process deposits, withdrawals, and balance queries
- send completed transactions back through the network

### `Network.java`
Represents the communication layer between the client and server.  
Contains:
- incoming buffer (`inComingPacket`)
- outgoing buffer (`outGoingPacket`)
- connection status variables
- synchronization logic for buffer coordination

---

## How the System Works
1. The sending client reads transactions from `transaction2.txt`
2. The client sends them into the **incoming network buffer**
3. One of the two server threads retrieves a transaction from the incoming buffer
4. The server processes the account operation
5. The completed transaction is placed into the **outgoing network buffer**
6. The receiving client reads the completed transaction and displays the result

---

## Synchronization Design

## Run 1 - No Account Protection
In Run 1, the methods:
- `deposit()`
- `withdraw()`
- `query()`

do **not** synchronize access to the shared account objects.  
This allows both server threads to update the same account at the same time.

### Result
This produces race conditions and inconsistent results.

---

## Run 2 - Account Synchronization
In Run 2, the shared account critical section is protected using:

```java
synchronized (account[i])
