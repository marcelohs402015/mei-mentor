# MEI-Mentor Frontend

Frontend React moderno e responsivo para o painel de oportunidades MEI-Mentor.

## 🚀 Stack Tecnológica

- **React 18** com TypeScript
- **Vite** (build tool)
- **TailwindCSS** (estilização)
- **Axios** (HTTP client)
- **Lucide React** (ícones)
- **React Circular Progressbar** (gráfico de score)

## 📋 Pré-requisitos

- Node.js 18+ ou superior
- npm ou yarn

## 🔧 Instalação

```bash
# Instalar dependências
npm install

# Iniciar servidor de desenvolvimento
npm run dev
```

A aplicação estará disponível em: `http://localhost:3000`

## 🏗️ Estrutura do Projeto

```
frontend/
├── src/
│   ├── components/          # Componentes React
│   │   ├── header.tsx       # Cabeçalho da aplicação
│   │   ├── search-bar.tsx   # Barra de busca de CPF
│   │   ├── opportunity-card.tsx  # Cards de impacto
│   │   ├── score-gauge.tsx  # Gráfico circular de score
│   │   ├── cta-button.tsx   # Botão de ação principal
│   │   ├── loading-spinner.tsx  # Spinner de carregamento
│   │   ├── error-message.tsx    # Mensagem de erro
│   │   └── opportunity-dashboard.tsx  # Dashboard completo
│   ├── services/            # Serviços de API
│   │   └── opportunity.service.ts
│   ├── lib/                 # Configurações
│   │   └── axios.ts         # Configuração do Axios
│   ├── types/               # TypeScript types
│   │   └── index.ts
│   ├── App.tsx              # Componente principal
│   ├── main.tsx             # Entry point
│   └── index.css            # Estilos globais
├── package.json
├── vite.config.ts
├── tailwind.config.js
└── tsconfig.json
```

## 🎨 Design System

### Paleta de Cores

- **Azul Profundo (Bank Blue)**: Confiança e segurança
  - `bank-blue-600`, `bank-blue-700`, `bank-blue-800`

- **Verde Esmeralda (Success Green)**: Sucesso e ganhos
  - `success-green-500`, `success-green-600`, `success-green-700`

- **Vermelho Tomate (Alert Red)**: Alerta e perdas
  - `alert-red-500`, `alert-red-600`, `alert-red-700`

### Componentes Principais

1. **Header**: Logo e título do sistema
2. **SearchBar**: Input para busca por CPF com validação
3. **OpportunityCard**: Cards de impacto (Perda, Limite, Score)
4. **ScoreGauge**: Gráfico circular mostrando score de 0-100
5. **CTAButton**: Botão de ação principal (pulsante)
6. **OpportunityDashboard**: Dashboard completo com todos os dados

## 🔌 Integração com API

O frontend consome a API em `http://localhost:8085`:

```typescript
GET /api/opportunity/{cpf}
```

### Exemplo de Uso

```bash
# Buscar análise para CPF
curl http://localhost:8085/api/opportunity/12345678901
```

## 📱 Responsividade

O design é **Mobile First** e totalmente responsivo:

- **Mobile**: Layout em coluna única
- **Tablet**: Grid de 2 colunas
- **Desktop**: Grid de 3 colunas para cards

## ✨ Funcionalidades

- ✅ Busca por CPF com validação
- ✅ Formatação automática de CPF
- ✅ Estados de loading e error
- ✅ Cards de impacto visual
- ✅ Gráfico circular de score
- ✅ Design responsivo
- ✅ Animações suaves
- ✅ Cores que transmitem confiança

## 🧪 Testando

### CPFs de Teste (do DataSeeder)

1. **Perfil A - Alvo Perfeito**: `12345678901`
2. **Perfil B - Assalariado**: `98765432100`
3. **Perfil C - Alto Faturamento**: `11122233344`

### Exemplo de Fluxo

1. Digite um CPF na barra de busca
2. Clique em "Buscar Oportunidades"
3. Visualize os cards de impacto
4. Veja o score no gráfico circular
5. Leia a recomendação
6. Clique no botão CTA para ação

## 🚀 Build para Produção

```bash
# Gerar build de produção
npm run build

# Preview do build
npm run preview
```

## 📝 Notas

- Certifique-se de que o backend está rodando em `http://localhost:8085`
- O CORS está configurado no backend para permitir requisições do frontend
- Os dados são formatados em Real Brasileiro (BRL)

---

**Desenvolvido com ❤️ para o Hackathon Financeiro**

