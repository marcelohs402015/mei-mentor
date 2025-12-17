# 🧪 Guia Completo de Testes - MEI-Mentor

Este documento fornece um guia completo para testar a API MEI-Mentor usando diferentes ferramentas.

## 📋 Índice

1. [Testes com Bruno](#testes-com-bruno)
2. [Testes com cURL](#testes-com-curl)
3. [Testes com Postman](#testes-com-postman)
4. [Testes com Swagger UI](#testes-com-swagger-ui)
5. [Validações e Casos de Teste](#validações-e-casos-de-teste)
6. [Testes de Frontend](#testes-de-frontend)

---

## 🎯 Testes com Bruno

### Pré-requisitos
- Bruno instalado: [usebruno.com](https://www.usebruno.com/)
- Backend rodando em `http://localhost:8085`

### Importar Coleção

1. **Abra o Bruno**
2. **Clique em "New Collection" ou "Import"**
3. **Selecione a pasta `bruno/` do projeto** (ou importe o arquivo `bruno/MEI-Mentor-API.bru`)
4. A coleção "MEI-Mentor API" será carregada com todos os testes configurados

**Arquivos da coleção:**
- `bruno/MEI-Mentor-API.bru` - Arquivo principal da coleção com variáveis
- `bruno/Get Opportunity Analysis - Perfil A.bru` - Teste do perfil A
- `bruno/Get Opportunity Analysis - Perfil B.bru` - Teste do perfil B
- `bruno/Get Opportunity Analysis - Perfil C.bru` - Teste do perfil C
- `bruno/Get Opportunity Analysis - CPF Inválido.bru` - Teste de erro

### Variáveis Configuradas

As variáveis já estão configuradas no arquivo `bruno/MEI-Mentor-API.bru`:

```javascript
vars {
  baseUrl: http://localhost:8085
  cpfPerfilA: 12345678901
  cpfPerfilB: 98765432100
  cpfPerfilC: 11122233344
}
```

### Testes Disponíveis

#### Teste 1: Perfil A (Alto Potencial)
- **Arquivo**: `bruno/Get Opportunity Analysis - Perfil A.bru`
- **CPF**: `12345678901`
- **O que testa**:
  - ✅ Status 200
  - ✅ Todos os campos presentes
  - ✅ Score alto (>= 70)
  - ✅ Perda mensal positiva
  - ✅ Limite sombra disponível
  - ✅ Market intelligence presente

**Resposta esperada:**
```json
{
  "id": "uuid",
  "customerId": "uuid",
  "potentialScore": 85,
  "monthlyLoss": 1300.00,
  "shadowLimit": 15000.00,
  "identifiedRevenue": 5000.00,
  "recommendation": "Alta oportunidade! ...",
  "marketIntelligence": {
    "businessNiche": "Confeitaria / Alimentação",
    "digitalPresenceScore": 85,
    "estimatedMaturity": "Em Expansão",
    "socialMediaPlatform": "Instagram",
    "socialMediaFollowers": 2500,
    "hasGoogleMapsPresence": true,
    "recommendedApproach": "..."
  }
}
```

#### Teste 2: Perfil B (Assalariado)
- **Arquivo**: `bruno/Get Opportunity Analysis - Perfil B.bru`
- **CPF**: `98765432100`
- **O que testa**:
  - ✅ Status 200
  - ✅ Score baixo (< 70)
  - ✅ Sem limite sombra

**Resposta esperada:**
```json
{
  "potentialScore": 20,
  "monthlyLoss": 0.00,
  "shadowLimit": 0.00,
  "identifiedRevenue": 0.00
}
```

#### Teste 3: Perfil C (Alto Faturamento)
- **Arquivo**: `bruno/Get Opportunity Analysis - Perfil C.bru`
- **CPF**: `11122233344`
- **O que testa**:
  - ✅ Status 200
  - ✅ Recomendação sugere ME/EPP
  - ✅ Receita identificada alta

**Resposta esperada:**
```json
{
  "recommendation": "Alerta: Faturamento identificado acima do limite MEI...",
  "identifiedRevenue": 20000.00
}
```

#### Teste 4: CPF Inválido
- **Arquivo**: `bruno/Get Opportunity Analysis - CPF Inválido.bru`
- **CPF**: `00000000000`
- **O que testa**:
  - ✅ Status 400 (Bad Request)
  - ✅ Mensagem de erro

**Resposta esperada:**
```json
{
  "timestamp": "2024-...",
  "status": 400,
  "error": "Bad Request",
  "message": "Customer not found for CPF: 00000000000"
}
```

### Executando Todos os Testes

1. No Bruno, selecione a coleção "MEI-Mentor API"
2. Clique em **"Run Collection"** ou use `Ctrl+R`
3. Todos os testes serão executados sequencialmente
4. Veja os resultados no painel de testes

---

## 💻 Testes com cURL

### Endpoint Base
```bash
GET http://localhost:8085/api/opportunity/{cpf}
```

### Teste 1: Perfil A (Alto Potencial)
```bash
curl -X GET "http://localhost:8085/api/opportunity/12345678901" \
  -H "Accept: application/json" \
  -H "Content-Type: application/json" | jq
```

**Resposta esperada:**
- Status: `200 OK`
- `potentialScore`: >= 70
- `monthlyLoss`: > 0
- `shadowLimit`: > 0
- `marketIntelligence`: presente

### Teste 2: Perfil B (Assalariado)
```bash
curl -X GET "http://localhost:8085/api/opportunity/98765432100" \
  -H "Accept: application/json" | jq
```

**Resposta esperada:**
- Status: `200 OK`
- `potentialScore`: < 70
- `shadowLimit`: 0
- `monthlyLoss`: 0 ou baixo

### Teste 3: Perfil C (Alto Faturamento)
```bash
curl -X GET "http://localhost:8085/api/opportunity/11122233344" \
  -H "Accept: application/json" | jq
```

**Resposta esperada:**
- Status: `200 OK`
- `recommendation`: contém "ME ou EPP"
- `identifiedRevenue`: > 10000

### Teste 4: CPF Inválido
```bash
curl -X GET "http://localhost:8085/api/opportunity/00000000000" \
  -H "Accept: application/json" | jq
```

**Resposta esperada:**
- Status: `400 Bad Request`
- `message`: "Customer not found for CPF: 00000000000"

### Script PowerShell (Windows)
```powershell
$baseUrl = "http://localhost:8085"

Write-Host "🧪 Testando API MEI-Mentor..." -ForegroundColor Cyan

Write-Host "`n✅ Teste 1: Perfil A" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/api/opportunity/12345678901" | ConvertTo-Json

Write-Host "`n✅ Teste 2: Perfil B" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/api/opportunity/98765432100" | ConvertTo-Json

Write-Host "`n✅ Teste 3: Perfil C" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/api/opportunity/11122233344" | ConvertTo-Json

Write-Host "`n✅ Teste 4: CPF Inválido" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/api/opportunity/00000000000"
} catch {
    Write-Host "Status: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
}
```

---

## 📮 Testes com Postman

### 1. Criar Nova Requisição

**Método**: `GET`  
**URL**: `http://localhost:8085/api/opportunity/12345678901`

### 2. Headers
```
Accept: application/json
Content-Type: application/json
```

### 3. Testes (Scripts)

Cole no tab "Tests":

```javascript
// Status code
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

// Response time
pm.test("Response time is less than 2000ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});

// Response structure
pm.test("Response has all required fields", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property("id");
    pm.expect(jsonData).to.have.property("potentialScore");
    pm.expect(jsonData).to.have.property("monthlyLoss");
    pm.expect(jsonData).to.have.property("shadowLimit");
    pm.expect(jsonData).to.have.property("marketIntelligence");
});

// Business logic
pm.test("Potential score is between 0-100", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.potentialScore).to.be.within(0, 100);
});

pm.test("Market intelligence is present", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.marketIntelligence).to.not.be.null;
    pm.expect(jsonData.marketIntelligence).to.have.property("digitalPresenceScore");
});
```

### 4. Criar Collection

1. Crie uma nova Collection: "MEI-Mentor API"
2. Adicione variáveis:
   - `baseUrl`: `http://localhost:8085`
   - `cpfPerfilA`: `12345678901`
   - `cpfPerfilB`: `98765432100`
   - `cpfPerfilC`: `11122233344`
3. Use as variáveis: `{{baseUrl}}/api/opportunity/{{cpfPerfilA}}`

---

## 🌐 Testes com Swagger UI

### Acessar Swagger
```
http://localhost:8085/swagger-ui.html
```

### Passos

1. **Localizar Endpoint**
   - Procure por "Customer Opportunity"
   - Expanda `GET /api/opportunity/{cpf}`

2. **Testar**
   - Clique em "Try it out"
   - Digite um CPF: `12345678901`
   - Clique em "Execute"

3. **Ver Resposta**
   - Status code
   - Response body (JSON formatado)
   - Response headers

4. **Ver Esquema**
   - Clique em "Schema" para ver a estrutura completa
   - Veja os campos obrigatórios e tipos

---

## ✅ Validações e Casos de Teste

### Casos de Teste Positivos

| CPF | Nome | Perfil | Validações Esperadas |
|-----|------|--------|---------------------|
| `12345678901` | João Silva | Alvo Perfeito | Score >= 70, Perda > 0, Limite > 0 |
| `98765432100` | Maria Santos | Assalariado | Score < 70, Limite = 0 |
| `11122233344` | Carlos Oliveira | Alto Faturamento | Recomendação ME/EPP, Receita > 10000 |

### Casos de Teste Negativos

| Cenário | CPF | Status Esperado | Mensagem |
|---------|-----|----------------|----------|
| CPF não existe | `00000000000` | `400` | "Customer not found" |
| CPF vazio | `` | `404` | Not Found |
| CPF inválido | `123` | `400` | Bad Request |

### Validações de Negócio

#### 1. Cálculo de Perda Mensal
```javascript
// Fórmula: (Receita × 27.5%) - R$ 75,00
// Exemplo: R$ 5.000 × 0.275 - R$ 75 = R$ 1.300
expect(monthlyLoss).to.equal(1300.00);
```

#### 2. Cálculo de Limite Sombra
```javascript
// Fórmula: Receita × 3 (se score >= 70)
// Exemplo: R$ 5.000 × 3 = R$ 15.000
if (potentialScore >= 70) {
    expect(shadowLimit).to.equal(identifiedRevenue * 3);
} else {
    expect(shadowLimit).to.equal(0);
}
```

#### 3. Score de Potencial
```javascript
// Deve estar entre 0-100
expect(potentialScore).to.be.within(0, 100);

// Bonus de +10 se presença digital alta
if (marketIntelligence.digitalPresenceScore >= 70) {
    // Score pode ser até 110, mas é limitado a 100
    expect(potentialScore).to.be.at.most(100);
}
```

#### 4. Market Intelligence
```javascript
// Sempre presente (mesmo que score seja 0)
expect(marketIntelligence).to.not.be.null;
expect(marketIntelligence.digitalPresenceScore).to.be.within(0, 100);
```

---

## 🖥️ Testes de Frontend

### Fluxo Completo de Teste

1. **Acesse o Frontend**
   ```
   http://localhost:3000
   ```

2. **Teste de Busca**
   - Digite CPF: `12345678901`
   - Clique em "Buscar Oportunidades"
   - Verifique se os cards aparecem

3. **Teste de Market Intelligence**
   - Verifique se o card "Raio-X de Mercado" aparece
   - Confirme que mostra:
     - Atividade Provável
     - Presença Digital (com estrelas)
     - Dica da IA

4. **Teste de Ativação**
   - Clique em "Ativar Conta MEI & Resgatar Limite"
   - Verifique se o modal de sucesso aparece
   - Confirme dados: Nome da empresa, CNPJ, Limite

5. **Teste do MEI-HUB**
   - Clique em "Acessar Minha Conta"
   - Verifique se o MEI-HUB carrega
   - Teste os mini-apps:
     - Emissor de Nota Fácil
     - Cobrança Pix
     - Benefícios MEI
   - Teste navegação de volta (botão "Sistema de Análise")

### Validações Visuais

- ✅ Cards com animações suaves
- ✅ Responsividade (mobile/desktop)
- ✅ Estados de loading
- ✅ Tratamento de erros
- ✅ Modal com animação
- ✅ Transições entre telas

---

## 🔍 Verificações de Integração

### 1. Verificar Data Seeding
```bash
# Acesse H2 Console
http://localhost:8085/h2-console

# JDBC URL: jdbc:h2:mem:meimentor
# Username: sa
# Password: (vazio)

# Execute:
SELECT * FROM customers;
SELECT * FROM transactions;
SELECT * FROM opportunity_analyses;
SELECT * FROM market_intelligence;
```

### 2. Verificar Logs
```bash
# Procure por:
# - "Starting data seeding..."
# - "Created Profile A: João Silva"
# - "Enriching profile for customer..."
# - "OpenAI service configured..." (se OpenAI estiver ativo)
```

### 3. Verificar CORS
```bash
# Teste de origem diferente
curl -X GET "http://localhost:8085/api/opportunity/12345678901" \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: GET" \
  -v
```

---

## 📊 Exemplo de Resposta Completa

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "customerId": "660e8400-e29b-41d4-a716-446655440001",
  "potentialScore": 85,
  "monthlyLoss": 1300.00,
  "shadowLimit": 15000.00,
  "identifiedRevenue": 5000.00,
  "recommendation": "Alta oportunidade! Você pode economizar R$ 1300.00 por mês formalizando como MEI. Limite de crédito pré-aprovado disponível. Presença digital confirmada: Presença em Instagram com 2.5k seguidores e cadastro no Google Maps.",
  "marketIntelligence": {
    "id": "770e8400-e29b-41d4-a716-446655440002",
    "customerId": "660e8400-e29b-41d4-a716-446655440001",
    "businessNiche": "Confeitaria / Alimentação",
    "digitalPresenceScore": 85,
    "estimatedMaturity": "Em Expansão",
    "recommendedApproach": "Cliente identificado com presença ativa no Instagram (@joaosilva) com 2500 seguidores. Negócio de confeitaria em expansão com cadastro no Google Maps. Abordagem recomendada: Destacar economia tributária e facilidade de emissão de notas fiscais para aumentar credibilidade com clientes. Oferecer limite de crédito como diferencial competitivo.",
    "socialMediaPlatform": "Instagram",
    "socialMediaFollowers": 2500,
    "hasGoogleMapsPresence": true
  }
}
```

---

## 🐛 Troubleshooting

### Erro: Connection refused
- **Causa**: Backend não está rodando
- **Solução**: Execute `mvn spring-boot:run`

### Erro: Customer not found
- **Causa**: DataSeeder não executou ou banco foi resetado
- **Solução**: Reinicie o backend para executar o DataSeeder novamente

### Erro: Timeout
- **Causa**: OpenAI está configurado mas não responde
- **Solução**: Verifique a chave da API ou desabilite temporariamente

### Erro: CORS
- **Causa**: Frontend e backend em portas diferentes
- **Solução**: CORS já está configurado, verifique se o backend está rodando

---

## 📝 Notas

- Os CPFs de teste são fictícios e usados apenas para demonstração
- O banco H2 é in-memory, então os dados são resetados a cada reinício
- O Market Intelligence usa mock se OpenAI não estiver configurado
- Todos os valores monetários estão em Real Brasileiro (BRL)
- A coleção Bruno está na pasta `bruno/` e pode ser importada diretamente

---

**Bons testes! 🚀**

