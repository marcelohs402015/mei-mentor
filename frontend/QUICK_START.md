# 🚀 Guia Rápido - MEI-Mentor Frontend

## Instalação Rápida

```bash
# 1. Entre na pasta frontend
cd frontend

# 2. Instale as dependências
npm install

# 3. Inicie o servidor de desenvolvimento
npm run dev
```

A aplicação abrirá automaticamente em `http://localhost:3000`

## ⚠️ Importante

**Certifique-se de que o backend está rodando em `http://localhost:8085`**

Se o backend não estiver rodando, você verá mensagens de erro ao tentar buscar análises.

## 🧪 Teste Rápido

1. Abra `http://localhost:3000`
2. Digite um dos CPFs de teste:
   - `12345678901` (Perfil A - Alvo Perfeito)
   - `98765432100` (Perfil B - Assalariado)
   - `11122233344` (Perfil C - Alto Faturamento)
3. Clique em "Buscar Oportunidades"
4. Visualize os cards de impacto e o gráfico de score

## 📱 Funcionalidades

✅ **Busca por CPF** com validação e formatação automática  
✅ **Cards de Impacto Visual**:
   - Dinheiro Deixado na Mesa (Perda Mensal)
   - Limite Disponível Pré-Aprovado (Shadow Limit)
   - Potencial Empreendedor (Score)
✅ **Gráfico Circular** mostrando score de 0-100%  
✅ **Recomendação Personalizada** baseada na análise  
✅ **Design Responsivo** (Mobile First)  
✅ **Estados de Loading e Error**  

## 🎨 Paleta de Cores

- **Azul Profundo**: Confiança e segurança (Header, Score)
- **Verde Esmeralda**: Sucesso e ganhos (Limite, CTA)
- **Vermelho Tomate**: Alerta e perdas (Perda Mensal)

## 🐛 Troubleshooting

### Erro: "Não foi possível conectar ao servidor"

- Verifique se o backend está rodando em `http://localhost:8085`
- Verifique se o CORS está configurado no backend
- Abra o console do navegador (F12) para mais detalhes

### Erro: "CPF deve conter 11 dígitos"

- Digite apenas números (a formatação é automática)
- O CPF deve ter exatamente 11 dígitos

### Porta 3000 já está em uso

- Altere a porta no arquivo `vite.config.ts`:
  ```typescript
  server: {
    port: 3001, // ou outra porta disponível
  }
  ```

---

**Pronto para usar! 🎉**

