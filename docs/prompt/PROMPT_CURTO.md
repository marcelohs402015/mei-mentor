# Prompt Curto para IA - Template de Projeto

Use este prompt ao iniciar um novo projeto no Cursor ou outra IA:

---

## 🎯 CONTEXTO

Crie um novo projeto seguindo esta arquitetura, stack tecnológica 

---

## 🏗️ ARQUITETURA: Hexagonal (Ports & Adapters)

**Estrutura Backend:**
```
com.{projeto}.{modulo}/
├── domain/          # Domínio puro (sem frameworks)
│   ├── model/       # Entidades Rich Domain Model
│   ├── port/        # Interfaces (contratos)
│   └── event/       # Domain Events
├── application/      # Casos de uso
│   ├── usecase/
│   └── saga/        # Orquestradores (se aplicável)
├── infrastructure/  # Adaptadores
│   ├── persistence/ # JPA adapters
│   ├── messaging/   # Kafka/RabbitMQ adapters
│   └── config/
└── presentation/    # REST API
    ├── controller/
    ├── dto/         # Java Records
    └── exception/
```

**Estrutura Frontend:**
```
src/
├── components/      # Componentes reutilizáveis
│   └── ui/         # UI base (Button, Input, etc.)
├── pages/           # Páginas/rotas
├── services/        # API services (Axios)
├── store/           # Zustand stores
├── types/           # TypeScript types
└── lib/             # Configurações (axios.ts)
```

---

## 🚀 STACK TECNOLÓGICA

### Backend
- **Java 21** (Virtual Threads habilitado)
- **Spring Boot 3.3.6+**
- **PostgreSQL** + **Flyway** (migrations)
- **Resilience4j** (Circuit Breaker, Retry)
- **Kafka** (Event-Driven)
- **Lombok** (`@RequiredArgsConstructor`, `@Getter`, `@Builder`)
- **Swagger/OpenAPI**
- **Maven**

### Frontend
- **React 18+** + **TypeScript 5.3+**
- **Vite 5.0+**
- **TailwindCSS 3.3+**
- **Zustand** (state management)
- **React Hook Form + Zod** (formulários)
- **Axios** (HTTP client)

---

## ✅ PADRÕES OBRIGATÓRIOS

### Backend
1. **Rich Domain Model**: Entidades com lógica de negócio, SEM anotações JPA
2. **Ports & Adapters**: Interfaces no domínio, implementações na infraestrutura
3. **Java Records** para DTOs (não classes)
4. **Lombok**: `@RequiredArgsConstructor` para DI, evitar `@Data` em entidades JPA
5. **Bean Validation**: `@NotNull`, `@NotBlank`, `@Valid`
6. **Javadoc** completo em classes públicas
7. **Global Exception Handler** com `@RestControllerAdvice`
8. **Virtual Threads** habilitado: `spring.threads.virtual.enabled=true`

### Frontend
1. **TypeScript strict**: tipos explícitos, evitar `any`
2. **TailwindCSS** para estilização (sem CSS customizado)
3. **Early returns** sempre que possível
4. **Event handlers**: prefixo `handle` (`handleClick`, `handleSubmit`)
5. **Consts**: `const func = () => {}` ao invés de `function func() {}`
6. **React Hook Form + Zod** para validação
7. **Zustand** para state global
8. **Axios** configurado em `lib/axios.ts`

---

## 📝 EXEMPLOS DE CÓDIGO

### Backend - Domain Model
```java
@Getter
@Builder
public class Order {
    private UUID id;
    private OrderStatus status;
    
    public Money calculateTotal() { /* lógica */ }
    public void updateStatus(OrderStatus newStatus) { /* validação */ }
}
```

### Backend - Port
```java
public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
}
```

### Backend - DTO (Record)
```java
public record CreateOrderRequest(
    @NotBlank String customerName,
    @Valid @NotNull List<OrderItemRequest> items
) {}
```

### Frontend - Componente
```typescript
interface ButtonProps {
  onClick: () => void;
  children: React.ReactNode;
}

const Button: React.FC<ButtonProps> = ({ onClick, children }) => {
  return (
    <button onClick={onClick} className="px-4 py-2 bg-blue-500 rounded">
      {children}
    </button>
  );
};
```

### Frontend - Service
```typescript
import { api } from '@/lib/axios';

export const orderService = {
  create: async (data: CreateOrderRequest): Promise<OrderResponse> => {
    const response = await api.post('/v1/orders', data);
    return response.data;
  },
};
```

---

## ❌ NÃO ACEITAR

- ❌ Anemic Domain Model (entidades sem lógica)
- ❌ Anotações JPA no domínio
- ❌ DTOs como classes (usar Records)
- ❌ CSS customizado (usar TailwindCSS)
- ❌ Componentes sem TypeScript
- ❌ Código sem documentação (Javadoc/TSDoc)
- ❌ Dependências do domínio em frameworks

---

## 🎯 INSTRUÇÕES PARA A IA

Ao criar o projeto:
1. Seguir **ESTRITAMENTE** esta arquitetura e stack
2. Aplicar **TODOS** os padrões listados
3. Criar estrutura de pastas completa
4. Configurar todas as dependências
5. Implementar exemplos básicos (CRUD mínimo)
6. Adicionar README com instruções de setup

**Domínio do novo projeto:** [INFORMAR AQUI]

---

**💡 Este prompt garante consistência arquitetural e tecnológica em todos os projetos.**

