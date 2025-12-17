# MEI-Mentor 🚀

Solução completa para análise de oportunidades de formalização MEI. Identifica empreendedores informais através de análise de transações financeiras, calcula economia potencial com formalização e oferece um painel visual moderno para apresentação dos resultados.

## 📦 Solução Completa

Este projeto é composto por:

- **Backend (Java/Spring Boot)**: API RESTful com lógica de análise e cálculos
- **Frontend (React/TypeScript)**: Painel visual moderno e responsivo

## 🎯 Objetivo

O MEI-Mentor analisa transações de clientes Pessoa Física (CPF) para:
- Identificar padrões comerciais em transações
- Calcular perda financeira por não estar formalizado
- Oferecer limite de crédito "sombra" (Shadow Limit) caso virem PJ
- Enriquecer perfis com inteligência de mercado usando IA (OpenAI)
- Apresentar resultados de forma visual e impactante
- Fornecer um ecossistema completo pós-ativação (MEI-HUB)

## 🏗️ Arquitetura

Este projeto segue a **Arquitetura Hexagonal (Ports & Adapters)**, garantindo:

- **Domain Layer**: Modelos de domínio puros (sem dependências de frameworks)
- **Application Layer**: Casos de uso e lógica de negócio
- **Infrastructure Layer**: Adaptadores JPA, repositórios, configurações
- **Presentation Layer**: Controllers REST, DTOs, Exception Handlers

## 🚀 Stack Tecnológica

### Backend
- **Java 21** (Virtual Threads habilitado)
- **Spring Boot 3.3.6**
- **H2 Database** (In-memory para demonstração)
- **Lombok** (redução de boilerplate)
- **SpringDoc OpenAPI** (Swagger UI)
- **OpenAI Java SDK** (integração com IA - opcional)
- **Maven** (gerenciamento de dependências)

### Frontend
- **React 18** com TypeScript
- **Vite** (build tool ultra-rápido)
- **TailwindCSS** (estilização moderna)
- **Axios** (HTTP client)
- **Lucide React** (ícones modernos)
- **SPA com roteamento condicional** (Análise ↔ MEI-HUB)

## 📋 Requisitos

### Backend
- Java 21 ou superior
- Maven 3.8+ ou superior

### Frontend
- Node.js 18+ ou superior
- npm ou yarn

## 🔧 Configuração e Execução

### 1. Clone o repositório

```bash
git clone <repository-url>
cd mei-mentor
```

### 2. Backend - Configuração e Execução

```bash
# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

O backend estará disponível em: `http://localhost:8085`

### 3. Frontend - Configuração e Execução

```bash
# Entre na pasta frontend
cd frontend

# Instale as dependências
npm install

# Execute o servidor de desenvolvimento
npm run dev
```

O frontend estará disponível em: `http://localhost:3000`

### 4. Acesse a Aplicação

1. Abra o navegador em `http://localhost:3000`
2. Digite um CPF de teste (veja seção abaixo)
3. Visualize os resultados no painel visual

## 📚 API Endpoints

### Analisar Oportunidade por CPF

```http
GET /api/opportunity/{cpf}
```

**Exemplo:**
```bash
curl http://localhost:8085/api/opportunity/12345678901
```

**Resposta:**
```json
{
  "id": "uuid",
  "customerId": "uuid",
  "potentialScore": 85,
  "monthlyLoss": 1300.00,
  "shadowLimit": 15000.00,
  "identifiedRevenue": 5000.00,
  "recommendation": "Alta oportunidade! Você pode economizar R$ 1300.00 por mês...",
  "marketIntelligence": {
    "businessNiche": "Confeitaria / Alimentação",
    "digitalPresenceScore": 85,
    "estimatedMaturity": "Em Expansão",
    "recommendedApproach": "Cliente identificado com presença ativa no Instagram...",
    "socialMediaPlatform": "Instagram",
    "socialMediaFollowers": 2500,
    "hasGoogleMapsPresence": true
  }
}
```

## 🎨 Frontend - Painel Visual

O frontend oferece uma experiência visual moderna com:

### Componentes Principais

- **Header**: Cabeçalho com logo e branding, com navegação entre telas
- **SearchBar**: Busca por CPF com validação e formatação automática
- **Opportunity Cards**: 3 cards de impacto visual:
  - 🔴 **Dinheiro Deixado na Mesa**: Perda mensal calculada
  - 🟢 **Limite Disponível Pré-Aprovado**: Shadow limit oferecido
  - 🔵 **Potencial Empreendedor**: Score de 0-100%
- **Market Intelligence Card**: 🟣 **Raio-X de Mercado** com dados de IA:
  - Atividade Provável (businessNiche)
  - Presença Digital (score com estrelas)
  - Redes sociais encontradas
  - Google Maps presence
  - **Dica da IA** (recommendedApproach)
- **Score Gauge**: Gráfico circular mostrando o potencial
- **Recomendação**: Mensagem personalizada baseada na análise
- **CTA Button**: Botão de ação principal para conversão
- **ActivationModal**: Modal de sucesso após ativação da conta MEI
- **MEI-HUB**: Tela completa pós-ativação com:
  - Capital de Giro Pré-Aprovado
  - Emissor de Nota Fácil (App integrado)
  - Cobrança Pix (App integrado)
  - Benefícios MEI (Plano de Saúde e DAS)
  - Estatísticas rápidas

### Design System

- **Paleta de Cores Profissional**:
  - Azul Profundo: Confiança e segurança
  - Verde Esmeralda: Sucesso e ganhos
  - Vermelho Tomate: Alerta e perdas
- **Mobile First**: Totalmente responsivo
- **Animações Suaves**: Transições e hovers elegantes

## 📊 Perfis de Clientes (Data Seeding)

A aplicação popula automaticamente 3 perfis de clientes ao iniciar:

### Perfil A - O Alvo Perfeito
- **Nome**: João Silva
- **CPF**: 12345678901
- **Perfil**: Vendedor de doces com múltiplos Pix recorrentes (R$ 50-200)
- **Faturamento**: ~R$ 5.000/mês
- **Status**: Alta oportunidade MEI

### Perfil B - O Assalariado
- **Nome**: Maria Santos
- **CPF**: 98765432100
- **Perfil**: Recebe apenas salário fixo
- **Status**: Não é alvo (sem atividade comercial)

### Perfil C - Alto Faturamento
- **Nome**: Carlos Oliveira
- **CPF**: 11122233344
- **Perfil**: Alto faturamento (R$ 20k/mês)
- **Status**: Excede limite MEI, deve sugerir ME/EPP

## 🔍 Documentação

### Swagger UI (Backend)

Acesse a documentação interativa da API em:
```
http://localhost:8085/swagger-ui.html
```

### OpenAPI JSON

Acesse o JSON da especificação OpenAPI em:
```
http://localhost:8085/api-docs
```

### Frontend README

Para mais detalhes sobre o frontend, consulte:
```
frontend/README.md
```

## 🧮 Lógica de Negócio

### Cálculo de Perda Mensal

```
Perda Mensal = (Receita Identificada × 27.5%) - R$ 75,00 (taxa MEI)
```

### Cálculo de Shadow Limit

```
Shadow Limit = Receita Identificada × 3 (se score >= 70)
```

### Cálculo de Score de Potencial (0-100)

- **Frequência de Transações Comerciais**: 40 pontos
- **Valor da Receita Identificada**: 40 pontos
- **Consistência de Transações**: 20 pontos
- **Bonus de Presença Digital**: +10 pontos (se digitalPresenceScore >= 70)

### Enriquecimento com IA (Market Intelligence)

O sistema enriquece perfis com dados de mercado usando:
- **OpenAI** (quando configurado via `OPENAI_API_KEY`)
- **Fallback automático** para dados mock se OpenAI não disponível
- **Análise de atividade provável** baseada em transações
- **Geração de recomendações personalizadas** para abordagem do cliente

## 🗄️ Banco de Dados

### H2 Console

Acesse o console H2 em:
```
http://localhost:8085/h2-console
```

**Credenciais:**
- JDBC URL: `jdbc:h2:mem:meimentor`
- Username: `sa`
- Password: (vazio)

## 🧪 Testando a Solução

### Via Frontend (Recomendado)

1. Acesse `http://localhost:3000`
2. Digite um dos CPFs de teste abaixo
3. Clique em "Buscar Oportunidades"
4. Visualize os resultados no painel visual
5. Explore o **Market Intelligence Card** (Raio-X de Mercado)
6. Clique em "Ativar Conta MEI & Resgatar Limite"
7. Veja o modal de sucesso e acesse o **MEI-HUB**

### Via API (cURL)

```bash
# Perfil A (Alta oportunidade)
curl http://localhost:8085/api/opportunity/12345678901

# Perfil B (Não é alvo)
curl http://localhost:8085/api/opportunity/98765432100

# Perfil C (Alto faturamento)
curl http://localhost:8085/api/opportunity/11122233344
```

### Via Swagger UI

1. Acesse `http://localhost:8085/swagger-ui.html`
2. Execute o endpoint `GET /api/opportunity/{cpf}`
3. Use os CPFs dos perfis acima

### Via Bruno (Coleção de Testes)

Consulte o guia completo de testes: `TESTES.md`

## 📁 Estrutura do Projeto

```
mei-mentor/
├── src/main/java/com/meimentor/    # Backend (Java/Spring Boot)
│   ├── customer/
│   │   ├── domain/
│   │   │   ├── model/          # Rich Domain Models
│   │   │   └── port/           # Interfaces (contratos)
│   │   ├── application/
│   │   │   └── usecase/        # Casos de uso
│   │   ├── infrastructure/
│   │   │   ├── persistence/    # JPA entities, repositories, adapters
│   │   │   └── config/         # Configurações (CORS, OpenAPI, DataSeeder)
│   │   └── presentation/
│   │       ├── controller/     # REST controllers
│   │       ├── dto/            # Data Transfer Objects (Records)
│   │       └── exception/       # Exception handlers
│   └── MeiMentorApplication.java
│
├── frontend/                      # Frontend (React/TypeScript)
│   ├── src/
│   │   ├── components/           # Componentes React
│   │   ├── services/             # Serviços de API
│   │   ├── lib/                  # Configurações (Axios)
│   │   ├── types/                # TypeScript types
│   │   └── App.tsx               # Componente principal
│   ├── package.json
│   └── vite.config.ts
│
├── pom.xml                        # Maven (Backend)
├── README.md                      # Este arquivo
└── docs/                          # Documentação adicional
```

## ✅ Padrões e Boas Práticas Aplicadas

### Backend
- ✅ **Rich Domain Model**: Entidades com lógica de negócio
- ✅ **Ports & Adapters**: Separação clara entre domínio e infraestrutura
- ✅ **Java Records**: DTOs como records
- ✅ **SOLID**: Princípios aplicados em toda a arquitetura
- ✅ **Clean Code**: Código limpo e manutenível
- ✅ **Javadoc**: Documentação completa em classes públicas
- ✅ **Bean Validation**: Validação de entrada
- ✅ **Global Exception Handler**: Tratamento centralizado de erros

### Frontend
- ✅ **TypeScript Strict**: Tipos explícitos, evitando `any`
- ✅ **Component-Based**: Componentes reutilizáveis e modulares
- ✅ **Mobile First**: Design responsivo mobile-first
- ✅ **Error Handling**: Tratamento robusto de erros
- ✅ **Loading States**: Estados de carregamento em todas as operações
- ✅ **Accessibility**: Boas práticas de acessibilidade
- ✅ **Clean Code**: Código limpo e legível

## 🚧 Próximos Passos

### Backend
- [ ] Migração para PostgreSQL (produção)
- [ ] Implementação de testes unitários e integração
- [ ] Adição de autenticação/autorização
- [ ] Implementação de cache (Redis)
- [ ] Integração com serviços externos (CPF validation, etc.)

### Frontend
- [ ] Implementação de testes (Jest + React Testing Library)
- [ ] Adição de gráficos mais detalhados (Recharts)
- [ ] Implementação de modo escuro
- [ ] Otimização de performance (lazy loading, code splitting)
- [ ] PWA (Progressive Web App)

## 🎯 Casos de Uso

### Para Analistas Bancários
1. Acesse o painel em `http://localhost:3000`
2. Digite o CPF do cliente
3. Visualize a análise completa com cards de impacto
4. Apresente os resultados ao cliente de forma visual

### Para Demonstração (Hackathon)
1. Use os CPFs de teste fornecidos
2. Demonstre a análise em tempo real
3. Mostre o cálculo de economia potencial
4. Apresente o limite de crédito pré-aprovado

## 📝 Licença

Este projeto foi desenvolvido para o Hackathon Financeiro.

---

## 🤖 Integração OpenAI (Opcional)

O sistema suporta enriquecimento com IA usando OpenAI:

### Configuração

1. Configure a variável de ambiente:
```powershell
$env:OPENAI_API_KEY = "sk-proj-..."
```

2. Opcionalmente, configure outros parâmetros:
```powershell
$env:OPENAI_MODEL = "gpt-4o-mini"
$env:OPENAI_TEMPERATURE = "0.0"
$env:OPENAI_MAX_TOKENS = "1000"
```

3. Reinicie o backend

### Comportamento

- **Com OpenAI**: Usa API real para gerar análises de mercado personalizadas
- **Sem OpenAI**: Usa dados mock (fallback automático)
- **Logs detalhados**: Console mostra todo o processo de enriquecimento

## 📚 Documentação Adicional

- **Visão Estratégica**: [`VISAO_ESTRATEGICA.md`](./VISAO_ESTRATEGICA.md) - Racional de negócio e estratégia do produto
- **Contexto do Projeto**: [`CONTEXTO_PROJETO.md`](./CONTEXTO_PROJETO.md) - Estado atual e arquitetura
- **Guia de Testes**: [`TESTES.md`](./TESTES.md) - Testes completos da API

## 📞 Suporte

Para dúvidas ou problemas:
- Consulte a documentação do Swagger: `http://localhost:8085/swagger-ui.html`
- Veja o guia de testes: `TESTES.md`
- Veja o README do frontend: `frontend/README.md`
- Verifique o guia rápido: `frontend/QUICK_START.md`

---

**Desenvolvido com ❤️ para o Hackathon Financeiro**

**MEI-Mentor** - Transformando análise de dados em oportunidades de negócio 🚀

