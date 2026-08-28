
## Spring has two major web programming models:
```
Spring MVC
Spring WebFlux
```
**Traditional**
```
Spring MVC
   ↓
Servlet API
   ↓
Tomcat
```

**Reactive**
```
Spring WebFlux
   ↓
Reactive Streams
   ↓
Netty / reactive server
```
---

**The reactive model is more designed like**
```
Request
   ↓
Start operation
   ↓
Thread does not have to sit there waiting
   ↓
Other work can be processed
   ↓
Database response arrives
   ↓
Reactive pipeline continues
   ↓
Response
```

This is particularly useful when an application has many concurrent I/O operations.

# Mono and Flux
 - we will see these every where in the spring web flux
 - Mono represent o or 1 value 
 - Mono<User>   means "maybe one user will arrive"

Flux

Represents:
 - Flux<User>
> 0  -> many values
> User 
> User
> User
> User
> ...

```
Mono<User>
     ↓
one result

Flux<User>
     ↓
multiple results
```


```
Mono = one future result

Flux = stream of future results
```

---
# Difference between Spring MVC and Spring Web Flux

|                        | Spring MVC                  | Spring WebFlux           |
| ---------------------- |-----------------------------|--------------------------|
| Programming model      | Traditional                 | Reactive                 |
| Common server          | Tomcat                      | Netty                    |
| Servlet API            | Yes                         | No                       |
| Main security object   | `SecurityFilterChain`       | `SecurityWebFilterChain` |
| Security configuration | `HttpSecurity`              | `ServerHttpSecurity`     |
| Authorization          | `authorizeHttpRequests()`   | `authorizeExchange()`    |
| Reactive types         | Not normally                | `Mono`, `Flux`           |
| Thread model           | Thread-per-request style    | Event-loop/reactive      |
| Best suited for        | Standard CRUD/business apps | High-concurrency I/O     |
| Spring Cloud Gateway   | No                          | Yes                      |


# OAUTH2 vs PKCE

 - pkce is just the extended topic of oauth2
 - in pkce(pixy) we have concept of code_verifier and code_challenge

## difference beteween them

| OAuth2                            | PKCE                                       |
| --------------------------------- | ------------------------------------------ |
| Authorization framework           | Security extension                         |
| Defines token/authorization flows | Protects authorization-code exchange       |
| Used to obtain access             | Protects the authorization code            |
| Broad concept                     | Specific mechanism                         |
| Used with Keycloak                | Used within OAuth2 Authorization Code flow |


