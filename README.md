# Spring Boot and Axon Framework Development Template

### Technologies
- Axon Framework
- Axon Server
- PostgreSQL
- H2
- Spring Boot
- Spring Security
- JWT Lib
- Spring AOP (Aspect Oriented Programming)
- Spring Data
- Spring Test
- Spring Validation
- Spring DevTools
- Spring Web
- Spring HATEOAS



## Axon Framework
Based on architectural principles, such as Domain-Driven Design (DDD) and Command-Query Responsibility Separation (CQRS), Axon Framework provides the building blocks that CQRS requires and helps to create scalable and extensible applications while maintaining application consistency in distributed systems. The open source Axon Framework provides a clean, elegant Java API for writing DDD, CQRS and Event Sourcing applications. It provides basic building blocks for writing aggregates, commands, queries, events, sagas, command handlers, event handlers, query handlers, repositories, communication buses and so on. It allows configuration hooks for pluggable infrastructure to support these building blocks. It utilizes intelligent defaults while allowing for configuration overrides. It supports popular technologies such as Spring Boot.
#### Scalability and Performance
CQRS and Axon distinguishes between two types of application components: query (read) and update (write) components. This means the information structure in these components can be optimized for the purpose at hand. As a result, query components have quick access to data that is already structured for its purpose. This in itself is a performance benefit, but also allows scaling for a specific component type.

#### Auditability and Transparency 
Application security involves, amongst others features, authentication, authorization and auditing. While the first two are quite common in applications, auditing is often overlooked or found too costly to implement. Axon is focused on Event Sourcing, providing the most reliable form of auditing. Besides security, auditing is a critical business and regulatory requirement for many applications.

#### Business Agility 
As software systems evolve during their lifetime, they often grow more complex. One proven way to manage complexity is decomposing the application to simple, loosely coupled, components. Axon provides clear APIs for components to communicate in a loosely coupled way. Loose coupling means components can be easily interchanged as business needs evolve. Besides making components much easier to adjust, loose coupling also makes adding new components easier. By simply connecting to an existing loosely coupled event, addition of new components doesn’t require existing components to be modified. This is a huge advantage over the more traditional imperative programming approach.

#### Application and Business Insights
The Event Sourcing paradigm championed by Axon is proven to provide for a valuable source of information for analytics and reporting. The Event Stream provides a reliable trail of everything that happened in the application, based on which new and unanticipated insights can be calculated, from day one. In addition because Axon is the central hub of all communication in the system, it can provide real time insight into what is going on in the application. This is especially important for debugging and tuning distributed applications.

### Axon Server
Axon Server makes it significantly easier for the user to setup and maintain the environment. 



## Core Principles
This section provides a list of design principles and best practices for building real-time, request/response and batch
oriented data management and data driven systems using Axon and Spring. The systems which are building around these principles are going be a multi-tenant, service-oriented, and event-driven architecture, and will be deployed in a cloud model or on-
premises.

#### Domain Driven Design First
We will take a close look at the solutions we are building, decomposed the functionality, and rearranged them based
on bounded contexts that we have identified. Simply, we do not develop systems based on developers assumptions.

#### Multi-tenancy First
There are many advantages to implementing a multi-tenant application environment. A multi-tenant application can
provide savings by reducing development and deployment costs to companies that develop applications. These savings
can be passed on to customers – increasing competitive advantages for all parties involved.
Savings created by multi-tenancy come from sharing the same resources with multiple tenants. Sharing resources
provides a way for an application vendor to create and maintain resources once for all customers, which can result in
significant savings.

#### Messaging First
A message broker (also known as an integration broker or interface engine) is an intermediary computer program
module that translates a message from the formal messaging protocol of the sender to the formal messaging protocol
of the receiver. We can achieve following benefits by using a message broker.

#### Event Driven Design
It is a design pattern built around the production, detection, and reaction to events that take place in time. It is a
design paradigm normalized for dynamic, asynchronous, process-oriented contexts.

#### Event Sourcing First 
With event sourcing, instead of storing the “current” state of the entities that are used in our system, we store a
stream of events that relate to these entities. Each event is a fact, it describes a state change that occurred to the
entity (past tense!). As we all know, facts are indisputable and immutable.
Having a stream of such events it’s possible to find out what’s the current state of an entity by folding all events
relating to that entity; note, however, that it’s not possible the other way round when storing the “current” state only,
we discard a lot of valuable historical information.

#### CQRS First 
Every service employs the Command and Query Responsibility Segregation (CQRS) pattern and Event Sourcing to
enable fast processing and data enhancement with a clean separation of concerns. CQRS segregates operations that
read data from operations that update data by using separate interfaces.
This pattern can maximize performance, scalability, and security; support evolution of the system over time through
higher flexibility; and prevent update commands from causing merge conflicts at the domain level. To allow scalability
and ephemeral behavior every services is stateless, not storing any data or holding context related information, and
supports multi-tenancy by default.

#### Stateless First
To maintain statelessness, and secure access at the same time, stateless authentication needs to performed via JWT
bearer tokens which need to be regularly refreshed. The tokens should be signed via a tenant-specific private key and
long-lived tokens are transmitted as secure cookies, thus limiting the possibilities for interception.
