import InfoModal from '../info-modal';
import { Wallet, Code, Zap, CheckCircle, ArrowRight, TrendingUp } from 'lucide-react';

interface CreditModalProps {
  isOpen: boolean;
  onClose: () => void;
  creditLimit: number;
}

const CreditModal: React.FC<CreditModalProps> = ({ isOpen, onClose, creditLimit }) => {
  return (
    <InfoModal
      isOpen={isOpen}
      onClose={onClose}
      title="Capital de Giro Pré-Aprovado"
      icon={<Wallet className="w-6 h-6 text-white" />}
      iconColor="bg-success-green-600"
    >
      <div className="space-y-6">
        {/* Valor Disponível */}
        <div className="bg-success-green-50 rounded-xl p-4 border-2 border-success-green-200">
          <p className="text-sm text-success-green-700 font-semibold mb-2">Limite Disponível</p>
          <p className="text-3xl font-bold text-success-green-900">
            {new Intl.NumberFormat('pt-BR', {
              style: 'currency',
              currency: 'BRL',
            }).format(creditLimit)}
          </p>
        </div>

        {/* Como Funciona */}
        <div>
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <Zap className="w-5 h-5 text-success-green-600" />
            <span>Como Funciona</span>
          </h3>
          <p className="text-gray-700 leading-relaxed">
            O Capital de Giro Pré-Aprovado é um limite de crédito disponibilizado imediatamente após 
            a formalização MEI. Você pode usar para investir no seu negócio, comprar equipamentos, 
            fazer estoque ou cobrir despesas operacionais.
          </p>
        </div>

        {/* Casos de Uso */}
        <div>
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <TrendingUp className="w-5 h-5 text-success-green-600" />
            <span>Casos de Uso</span>
          </h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
            <div className="bg-gray-50 rounded-lg p-3">
              <p className="font-semibold text-gray-800 mb-1">💰 Compra de Equipamentos</p>
              <p className="text-sm text-gray-600">Adquira máquinas, ferramentas ou tecnologia para aumentar produtividade</p>
            </div>
            <div className="bg-gray-50 rounded-lg p-3">
              <p className="font-semibold text-gray-800 mb-1">📦 Formação de Estoque</p>
              <p className="text-sm text-gray-600">Compre matéria-prima em maior volume com melhor preço</p>
            </div>
            <div className="bg-gray-50 rounded-lg p-3">
              <p className="font-semibold text-gray-800 mb-1">🎯 Marketing e Vendas</p>
              <p className="text-sm text-gray-600">Invista em publicidade, redes sociais ou campanhas promocionais</p>
            </div>
            <div className="bg-gray-50 rounded-lg p-3">
              <p className="font-semibold text-gray-800 mb-1">⚡ Capital de Giro</p>
              <p className="text-sm text-gray-600">Cubra despesas enquanto aguarda recebimentos de clientes</p>
            </div>
          </div>
        </div>

        {/* Benefícios */}
        <div>
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <CheckCircle className="w-5 h-5 text-success-green-600" />
            <span>Vantagens</span>
          </h3>
          <ul className="space-y-2 text-gray-700">
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Liberação imediata após ativação da conta MEI</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Taxas competitivas e condições especiais para MEI</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Sem necessidade de garantias adicionais</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Controle total via MEI-HUB</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Parcelamento flexível conforme seu fluxo de caixa</span>
            </li>
          </ul>
        </div>

        {/* Implementação Técnica */}
        <div className="bg-gray-50 rounded-xl p-4 border-l-4 border-success-green-500">
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <Code className="w-5 h-5 text-success-green-600" />
            <span>Como Implementar</span>
          </h3>
          <div className="space-y-3 text-sm text-gray-700">
            <div>
              <p className="font-semibold mb-1">1. Sistema de Crédito:</p>
              <p className="pl-4">
                Integrar com sistema de crédito do banco ou fintech parceira. O limite já foi 
                pré-aprovado na análise inicial (Shadow Limit).
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">2. Backend (Java/Spring Boot):</p>
              <p className="pl-4">
                Criar endpoints: <code className="bg-gray-200 px-2 py-1 rounded">POST /api/credit/request</code> 
                para solicitar uso, <code className="bg-gray-200 px-2 py-1 rounded">GET /api/credit/balance</code> 
                para consultar saldo disponível, e <code className="bg-gray-200 px-2 py-1 rounded">GET /api/credit/transactions</code> 
                para histórico.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">3. Frontend (React):</p>
              <p className="pl-4">
                Tela de solicitação com formulário (valor, finalidade, parcelas). Dashboard com 
                saldo disponível, histórico de transações e simulação de parcelas.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">4. Funcionalidades Adicionais:</p>
              <ul className="pl-4 list-disc list-inside space-y-1">
                <li>Simulador de parcelas e taxas</li>
                <li>Notificações de vencimento e pagamento</li>
                <li>Integração com conta corrente para débito automático</li>
                <li>Programa de fidelidade com aumento de limite</li>
                <li>Análise de uso para otimização de crédito</li>
              </ul>
            </div>
          </div>
        </div>

        {/* Próximos Passos */}
        <div className="bg-success-green-50 rounded-xl p-4">
          <h3 className="text-lg font-bold text-success-green-900 mb-2 flex items-center space-x-2">
            <ArrowRight className="w-5 h-5" />
            <span>Próximos Passos</span>
          </h3>
          <p className="text-success-green-800 text-sm">
            Esta funcionalidade pode ser desenvolvida em parceria com a área de crédito do banco. 
            O diferencial é que o limite já foi pré-aprovado durante a análise de oportunidade, 
            eliminando burocracia e acelerando o acesso ao crédito para o MEI.
          </p>
        </div>
      </div>
    </InfoModal>
  );
};

export default CreditModal;

