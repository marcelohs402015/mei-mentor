# 📋 Contexto do Projeto MEI-Mentor

Documento de contexto e estado atual do projeto.

> 💡 **Para entender a visão estratégica e o racional de negócio completo, consulte:** [`VISAO_ESTRATEGICA.md`](./VISAO_ESTRATEGICA.md)

## 🎯 Objetivo do Projeto

**MEI-Mentor** é uma solução para Hackathon Financeiro que:
- Analisa transações de clientes PF (CPF) para identificar empreendedores informais
- Calcula quanto dinheiro estão perdendo por não serem formalizados
- Oferece limite de crédito "sombra" (Shadow Limit) caso virem PJ
- Enriquece perfis com inteligência de mercado usando IA (OpenAI)

## 🏗️ Arquitetura

### Backend (Java/Spring Boot)
- **Arquitetura**: Hexagonal (Ports & Adapters)
- **Java**: 21 (Virtual Threads habilitado)
- **Spring Boot**: 3.3.6
- **Banco**: H2 (In-memory para demonstração)
- **Porta**: 8085

### Frontend (React/TypeScript)
- **React**: 18 com TypeScript
- **Vite**: Build tool
- **TailwindCSS**: Estilização
- **Porta**: 3000

## 📦 Estrutura do Backend

```
src/main/java/com/meimentor/
├── customer/
│   ├── domain/
│   │   ├── model/              # Rich Domain Models
│   │   │   ├── Customer.java
│   │   │   ├── Transaction.java
│   │   │   ├── OpportunityAnalysis.java
│   │   │   └── MarketIntelligence.java  # NOVO
│   │   └── port/               # Interfaces
│   │       ├── CustomerRepositoryPort.java
│   │       ├── TransactionRepositoryPort.java
│   │       └── OpportunityAnalysisRepositoryPort.java
│   ├── application/
│   │   └── usecase/
│   │       ├── OpportunityService.java
│   │       └── EnrichmentService.java   # NOVO - IA
│   ├── infrastructure/
│   │   ├── persistence/
│   │   │   ├── entity/
│   │   │   │   ├── CustomerEntity.java
│   │   │   │   ├── TransactionEntity.java
│   │   │   │   ├── OpportunityAnalysisEntity.java
│   │   │   │   └── MarketIntelligenceEntity.java  # NOVO
│   │   │   ├── repository/
│   │   │   │   ├── CustomerJpaRepository.java
│   │   │   │   ├── TransactionJpaRepository.java
│   │   │   │   ├── OpportunityAnalysisJpaRepository.java
│   │   │   │   └── MarketIntelligenceJpaRepository.java  # NOVO
│   │   │   └── adapter/
│   │   │       ├── CustomerRepositoryAdapter.java
│   │   │       ├── TransactionRepositoryAdapter.java
│   │   │       └── OpportunityAnalysisRepositoryAdapter.java
│   │   └── config/
│   │       ├── CorsConfig.java
│   │       ├── DataSeeder.java
│   │       ├── OpenApiConfig.java
│   │       └── OpenAIConfig.java  # NOVO
│   └── presentation/
│       ├── controller/
│       │   └── CustomerController.java
│       ├── dto/
│       │   ├── OpportunityAnalysisResponse.java
│       │   └── MarketIntelligenceResponse.java  # NOVO
│       └── exception/
│           ├── GlobalExceptionHandler.java
│           └── ErrorResponse.java
```

## 🧮 Lógica de Negócio Implementada

### 1. Cálculo de Perda Mensal
```
Perda Mensal = (Receita Identificada × 27.5%) - R$ 75,00 (taxa MEI)
```

### 2. Cálculo de Shadow Limit
```
Shadow Limit = Receita Identificada × 3 (se score >= 70)
```

### 3. Score de Potencial (0-100)
- Frequência de Transações Comerciais: 40 pontos
- Valor da Receita Identificada: 40 pontos
- Consistência de Transações: 20 pontos
- **Bonus de Presença Digital**: +10 pontos (se digitalPresenceScore >= 70)

### 4. EnrichmentService (IA)
- Usa OpenAI quando configurado (via `OPENAI_API_KEY`)
- Fallback para dados mock se OpenAI não disponível
- Identifica atividade provável das transações
- Gera análise de mercado com:
  - Business Niche
  - Digital Presence Score
  - Estimated Maturity
  - Recommended Approach

## 📊 Dados de Teste (DataSeeder)

### Perfil A - Alvo Perfeito
- **CPF**: `12345678901`
- **Nome**: João Silva
- **Perfil**: Vendedor de doces
- **Faturamento**: ~R$ 5.000/mês
- **Transações**: 30 Pix comerciais + 10 débitos

### Perfil B - Assalariado
- **CPF**: `98765432100`
- **Nome**: Maria Santos
- **Perfil**: Assalariada
- **Transações**: Apenas salário fixo

### Perfil C - Alto Faturamento
- **CPF**: `11122233344`
- **Nome**: Carlos Oliveira
- **Perfil**: Alto faturamento (~R$ 20k/mês)
- **Status**: Excede limite MEI, sugere ME/EPP

## 🔌 Endpoints Disponíveis

### REST API
- `GET /api/opportunity/{cpf}` - Analisa oportunidade com Market Intelligence

### Documentação
- `GET /swagger-ui.html` - Swagger UI
- `GET /api-docs` - OpenAPI JSON

### Desenvolvimento
- `GET /h2-console` - Console H2 (dev)

## 🎨 Frontend - Componentes

### Componentes Principais
1. **Header** - Cabeçalho com logo
2. **SearchBar** - Busca por CPF com validação
3. **OpportunityCard** - Cards de impacto (3 cards)
4. **ScoreGauge** - Gráfico circular de score
5. **MarketIntelligenceCard** - NOVO - Raio-X de Mercado com IA
6. **OpportunityDashboard** - Dashboard completo
7. **CTAButton** - Botão de ação
8. **LoadingSpinner** - Spinner de carregamento
9. **ErrorMessage** - Mensagens de erro

### Market Intelligence Card
- **Cores**: Roxo/Índigo (tema IA)
- **Ícones**: Sparkles, Wifi, Star, MapPin
- **Exibe**:
  - Atividade Provável (businessNiche)
  - Presença Digital com estrelas (Alta/Média/Baixa)
  - Redes sociais encontradas
  - Google Maps presence
  - Maturidade do negócio
  - **Dica da IA** (recommendedApproach) em destaque

## 🤖 Integração OpenAI

### Configuração
- Variável de ambiente: `OPENAI_API_KEY`
- Model: `gpt-4o-mini` (configurável via `OPENAI_MODEL`)
- Temperature: `0.0` (configurável via `OPENAI_TEMPERATURE`)
- Max Tokens: `1000` (configurável via `OPENAI_MAX_TOKENS`)

### Comportamento
- Se OpenAI configurado: Usa API real para enriquecimento
- Se não configurado: Usa dados mock (fallback automático)
- Logs detalhados no console mostrando o processo

### Logs no Console
```
🤖 OpenAI está configurado! Tentando enriquecer com IA...
📤 Enviando prompt para OpenAI:
   Modelo: gpt-4o-mini
   Prompt: Analise o perfil...
⏳ Aguardando resposta da OpenAI...
📥 Resposta recebida da OpenAI: {...}
✅ Dados parseados: Niche=..., Score=..., Maturity=...
```

## 📝 Tipos TypeScript (Frontend)

```typescript
interface MarketIntelligenceResponse {
  id: string;
  customerId: string;
  businessNiche: string | null;
  digitalPresenceScore: number | null;
  estimatedMaturity: string | null;
  recommendedApproach: string | null;
  socialMediaPlatform: string | null;
  socialMediaFollowers: number | null;
  hasGoogleMapsPresence: boolean | null;
}

interface OpportunityAnalysisResponse {
  id: string;
  customerId: string;
  potentialScore: number;
  monthlyLoss: number;
  shadowLimit: number;
  identifiedRevenue: number;
  recommendation: string;
  marketIntelligence: MarketIntelligenceResponse | null;  // NOVO
}
```

## 🔧 Configurações Importantes

### application.properties
- Porta: `8085`
- H2 Database: In-memory
- Virtual Threads: Habilitado
- OpenAI: Configurado para ler variáveis de ambiente

### Variáveis de Ambiente (PowerShell)
```powershell
$env:OPENAI_API_KEY = "sk-proj-..."
$env:OPENAI_MODEL = "gpt-4o-mini"
$env:OPENAI_TEMPERATURE = "0.0"
$env:OPENAI_MAX_TOKENS = "1000"
```

## ✅ Status Atual

### Backend
- ✅ Estrutura Hexagonal completa
- ✅ Domain Models (Rich Domain)
- ✅ Services implementados
- ✅ Integração OpenAI funcional
- ✅ Data Seeder com 3 perfis
- ✅ Swagger/OpenAPI configurado
- ✅ CORS configurado
- ✅ Exception Handler global

### Frontend
- ✅ Estrutura React + TypeScript
- ✅ TailwindCSS configurado
- ✅ Componentes principais criados
- ✅ Market Intelligence Card implementado
- ✅ Integração com API
- ✅ Estados de loading/error
- ✅ Design responsivo

## 🚧 Próximos Passos (Sugestões)

### Backend
- [ ] Testes unitários
- [ ] Testes de integração
- [ ] Migração para PostgreSQL
- [ ] Cache (Redis)
- [ ] Autenticação/Autorização

### Frontend
- [ ] Testes (Jest + React Testing Library)
- [ ] Gráficos mais detalhados
- [ ] Modo escuro
- [ ] PWA

## 📚 Documentação

- **README.md** - Documentação principal
- **TESTES_API.md** - Guia de testes (Bruno, cURL, Postman, Swagger)
- **API_ENDPOINTS.md** - Documentação de endpoints
- **bruno/** - Coleção de testes Bruno

## 🔑 Pontos Importantes

1. **OpenAI é opcional**: Sistema funciona com ou sem OpenAI
2. **Logs detalhados**: Console mostra todo o processo de enriquecimento
3. **Fallback automático**: Se OpenAI falhar, usa mock
4. **Bonus de Score**: +10 pontos se presença digital alta
5. **Market Intelligence sempre presente**: Mesmo que score seja 0

---

**Última atualização**: 2024-12-16
**Status**: ✅ Funcional e pronto para demonstração

