# Sistema de Catálogo de Produtos e Simulação de Pedidos

## Descrição do Desafio
Desenvolvimento de um sistema de catálogo de produtos e simulação de pedidos composto por dois microsserviços independentes, implementados com Spring Boot e Spring Cloud. A comunicação entre os serviços utiliza requisições HTTP via API Gateway com Service Discovery.

## Contexto do Negócio
Sistema de gestão de pedidos com catálogo de produtos, contendo:

### Microservice 1 (Catálogo de Produtos)
- Cadastro, listagem e consulta de produtos (nome, descrição, preço)
- Persistência de dados via H2 Database

### Microservice 2 (Simulador de Pedidos)
- Consulta ao Microservice 1 para obter produtos disponíveis
- Simulação de criação de pedidos com lista de produtos
- **Não requer persistência**

## Requisitos Técnicos
| Componente              | Tecnologia          |
|-------------------------|---------------------|
| Framework Principal     | Spring Boot         |
| Service Discovery       | Spring Cloud Eureka |
| API Gateway             | Spring Cloud Gateway|
| Banco de Dados (MS1)    | H2 Database         |

### Acessibilidade
| Serviço               | Rota Base       |
|-----------------------|----------------|
| Catálogo de Produtos  | `/produtos`    |
| Simulador de Pedidos  | `/pedidos`     |

**Regra:** Todos os endpoints devem ser acessados exclusivamente via Gateway

## Diagrama de Arquitetura
![image](https://github.com/user-attachments/assets/b7e1ee75-2898-475b-8ebb-1ec3e0eb694b)

## Endpoints

### Microservice 1 (Catálogo)

GET    /produtos         Listar todos os produtos<br>
POST   /produtos         Criar novo produto<br>
GET    /produtos/{id}    Consultar produto por ID<br>
### Microservice 2 (Pedidos)

GET    /pedidos/simular  Simular pedido (com lista de IDs de produtos)<br>

## Fluxo de Comunicação
![image](https://github.com/user-attachments/assets/e1df992c-5050-4fb0-8463-44d0f3a6fe25)

## Estrutura do Projeto
![image](https://github.com/user-attachments/assets/a7982b25-61bb-48d1-a75c-54a3f3212da5)

## Configuração do Service Discovery (Eureka)
```yaml
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

spring:
  application:
    name: discovery-server
```

## Configuração do API Gateway
```yaml
server:
  port: 8080

spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      server:
        webflux:
          routes:
            - id: product-service
              uri: lb://produto-service
              predicates:
                - Path=/product-service/**
              filters:
                - StripPrefix=1  # Remove o prefixo /product-service

            - id: product-service-h2-console
              uri: lb://produto-service
              predicates:
                - Path=/product-service/h2-console/**
              filters:
                - RewritePath=/product-service/h2-console/(?<segment>.*), /h2-console/$\{segment}

            - id: order-service
              uri: lb://pedido-service
              predicates:
                - Path=/order-service/**
              filters:
                - StripPrefix=1  # Remove o prefixo /order-service
                - name: Retry
                  args:
                    retries: 3
                    statuses: SERVICE_UNAVAILABLE
          
          discovery:
            locator:
              enabled: true

management:
  endpoints:
    web:
      exposure:
        include: "*"  # Expõe todos os endpoints do actuator
      base-path: /actuator  # Define o caminho base
  endpoint:
    health:
      show-details: always
    gateway:
      enabled: true  # Habilita endpoints específicos do Gateway

# Configuração de logging para diagnóstico (opcional)
logging:
  level:
    org.springframework.cloud.gateway: TRACE
    org.springframework.http.server.reactive: DEBUG
    reactor.netty: DEBUG
    org.springframework.cloud.loadbalancer: DEBUG
```

## Testando a Aplicação
### Acessar Eureka Dashboard:
http://localhost:8761

![image](https://github.com/user-attachments/assets/d777d915-4353-4cce-9cc3-897e975d8165)

### Registrar produto via Gateway:
```bash
curl -X POST http://localhost:8080/produtos \
-H "Content-Type: application/json" \
-d '{"nome":"Notebook", "descricao":"i7 16GB RAM", "preco":4500.00}'
```
### 1. Listagem de Produtos via Gateway
```bash
curl http://localhost:8080/PRODUTO-SERVICE/produtos
```
##### Descrição:<br>
Teste de acesso ao catálogo de produtos (Microservice 1) através do API Gateway.<br>
##### Verificação:<br>
Gateway roteando corretamente para o serviço de produtos<br>
Funcionamento do endpoint de listagem de produtos<br>
Formato JSON com dados completos (ID, nome, descrição, preço)<br>
```json
[
  {"id":1,"name":"Tablet","description":"10 polegadas 64GB","price":1200.0},
  {"id":2,"name":"Smartwatch","description":"Monitor Cardíaco 4GB","price":800.0},
  {"id":3,"name":"Fone de Ouvido","description":"Bluetooth Noise Cancelling","price":350.0},
  {"id":4,"name":"Monitor","description":"27 polegadas 4K","price":2000.0},
  {"id":5,"name":"Teclado Mecânico","description":"RGB Switch Blue","price":400.0}
]
```

### 2. Listagem de Pedidos Simulados
```bash
curl http://localhost:8080/PEDIDO-SERVICE/pedidos
```
##### Descrição:<br>
Teste do simulador de pedidos (Microservice 2) via Gateway.<br>
##### Verificação:<br>
Roteamento do Gateway para o serviço de pedidos<br>
Funcionamento básico do endpoint de pedidos<br>
Formato de resposta textual simples<br>
```text
Lista de pedidos: [Pedido 1001, Pedido 1002]
```
### 3. Inspeção das Rotas do Gateway
```bash
curl http://localhost:8080/actuator/gateway/routes
```
##### Descrição:<br>
Verificação da configuração de roteamento do Spring Cloud Gateway.<br>
##### Análise da Resposta:<br>
PRODUTO-SERVICE: Roteado para ```lb://PRODUTO-SERVICE com filtro RewritePath ```<br>
PEDIDO-SERVICE: Roteado para ```lb://PEDIDO-SERVICE com política de Retry (3 tentativas) ```<br>
Rotas adicionais para H2 Console e serviços alternativos<br>
Configuração de ```StripPrefix ``` para remoção de prefixos de URL<br>
```yaml
filters:
- "[[RewritePath /PRODUTO-SERVICE/?(?<remaining>.*) = '/${remaining}']]"
- "[[Retry routeId = 'order-service', retries = 3]]"  # Resiliência
```

### 4. Verificação de Saúde do Sistema
```bash
curl http://localhost:8080/actuator/health
```
##### Descrição:<br>
Teste de saúde completo do sistema através do endpoint Actuator.<br>
##### Componentes Verificados:<br>
Status Geral:```text 
"status":"UP" ```<br>
Service Discovery (Eureka):<br>
Serviços registrados:``` ["gateway-service","produto-service","pedido-service"]```<br>
Status remoto:``` "eureka":{"status":"UP"} ```<br>
##### Espaço em Disco:<br>
```json
"diskSpace": {
  "status":"UP",
  "free":125976379392,  # ~117GB livres
  "threshold":10485760  # 10MB mínimo
}
```
Reactive Discovery Clients:<br>
Lista de serviços ativos confirmada<br>
Outros Componentes:<br>
SSL: UP<br>
RefreshScope: UP<br>
Ping: UP<br>

```http
