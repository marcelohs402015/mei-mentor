import { useState } from 'react';
import { 
  Wallet, 
  FileText, 
  MessageCircle, 
  Heart, 
  Receipt, 
  ArrowLeft,
  CheckCircle,
  Clock,
  DollarSign
} from 'lucide-react';
import InvoiceModal from './modals/invoice-modal';
import CreditModal from './modals/credit-modal';
import WhatsAppModal from './modals/whatsapp-modal';

interface MeiHubProps {
  companyName: string;
  cnpj: string;
  creditLimit: number;
  onBack: () => void;
}

const MeiHub: React.FC<MeiHubProps> = ({ companyName, cnpj, creditLimit, onBack }) => {
  const [lastInvoice] = useState({ amount: 500.00, client: 'Ana Silva', date: '15/12/2024' });
  const [pendingReceivables] = useState(1200.00);
  const [healthPlan] = useState({ active: true, provider: 'Unimed' });
  const [dasStatus] = useState({ paid: true, nextDue: '20/12/2024' });
  
  // Modal states
  const [showInvoiceModal, setShowInvoiceModal] = useState(false);
  const [showCreditModal, setShowCreditModal] = useState(false);
  const [showWhatsAppModal, setShowWhatsAppModal] = useState(false);

  const handleUseCredit = () => {
    setShowCreditModal(true);
  };

  const handleEmitInvoice = () => {
    setShowInvoiceModal(true);
  };

  const handleCollectPix = () => {
    setShowWhatsAppModal(true);
  };

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between flex-wrap gap-4">
            <div className="flex items-center space-x-4">
              <div>
                <h1 className="text-xl font-bold text-gray-800">MEI-HUB</h1>
                <p className="text-sm text-gray-600">Área do Cliente</p>
              </div>
            </div>
            <div className="flex items-center space-x-4">
              <button
                onClick={onBack}
                className="bg-bank-blue-600 text-white px-4 py-2 rounded-lg hover:bg-bank-blue-700 transition-colors text-sm font-medium shadow-md hover:shadow-lg flex items-center space-x-2"
                title="Voltar para tela de análise"
              >
                <ArrowLeft className="w-4 h-4" />
                <span>Nova Análise</span>
              </button>
              <div className="text-right hidden sm:block">
                <p className="text-sm font-medium text-gray-700">{companyName}</p>
                <p className="text-xs text-gray-500">CNPJ: {cnpj}</p>
              </div>
            </div>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        {/* Hero Section - Capital de Giro */}
        <div className="mb-8">
          <div className="bg-gradient-to-br from-success-green-500 to-success-green-600 rounded-2xl shadow-xl p-8 text-white">
            <div className="flex items-center justify-between flex-wrap gap-4">
              <div>
                <p className="text-success-green-100 text-sm font-semibold uppercase mb-2">
                  Capital de Giro Pré-Aprovado
                </p>
                <p className="text-4xl md:text-5xl font-bold mb-2">
                  {new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                  }).format(creditLimit)}
                </p>
                <p className="text-success-green-100 text-sm">
                  Disponível para uso imediato
                </p>
              </div>
              <button
                onClick={handleUseCredit}
                className="bg-white text-success-green-600 font-bold py-4 px-8 rounded-xl hover:bg-success-green-50 transition-all duration-300 shadow-lg hover:shadow-xl transform hover:scale-105 flex items-center space-x-2"
              >
                <Wallet className="w-5 h-5" />
                <span>Usar Crédito</span>
              </button>
            </div>
          </div>
        </div>

        {/* Welcome Message */}
        <div className="mb-8">
          <h2 className="text-2xl font-bold text-gray-800 mb-2">
            Olá, {companyName}! 👋
          </h2>
          <p className="text-gray-600">
            Bem-vindo ao seu ecossistema MEI. Gerencie seu negócio de forma simples e eficiente.
          </p>
        </div>

        {/* Mini-Apps Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          {/* App 1: Emissor de Nota Fácil */}
          <div className="bg-white rounded-2xl shadow-card p-6 hover:shadow-lg transition-all duration-300">
            <div className="flex items-center justify-between mb-4">
              <div className="bg-bank-blue-100 p-3 rounded-xl">
                <FileText className="w-6 h-6 text-bank-blue-600" />
              </div>
              <span className="text-xs font-semibold text-bank-blue-600 uppercase">App Integrado</span>
            </div>
            
            <h3 className="text-lg font-bold text-gray-800 mb-2">Emissor de Nota Fácil</h3>
            <p className="text-sm text-gray-600 mb-4">
              Emita notas fiscais de serviço de forma rápida e simples
            </p>

            <div className="bg-gray-50 rounded-lg p-3 mb-4">
              <p className="text-xs text-gray-500 mb-1">Última nota emitida</p>
              <p className="text-sm font-semibold text-gray-800">
                {new Intl.NumberFormat('pt-BR', {
                  style: 'currency',
                  currency: 'BRL',
                }).format(lastInvoice.amount)} para {lastInvoice.client}
              </p>
              <p className="text-xs text-gray-500 mt-1">{lastInvoice.date}</p>
            </div>

            <button
              onClick={handleEmitInvoice}
              className="w-full bg-bank-blue-600 text-white font-semibold py-3 px-4 rounded-lg hover:bg-bank-blue-700 transition-colors flex items-center justify-center space-x-2"
            >
              <FileText className="w-4 h-4" />
              <span>Emitir NFS-e</span>
            </button>
          </div>

          {/* App 2: Cobrança Pix */}
          <div className="bg-white rounded-2xl shadow-card p-6 hover:shadow-lg transition-all duration-300">
            <div className="flex items-center justify-between mb-4">
              <div className="bg-success-green-100 p-3 rounded-xl">
                <MessageCircle className="w-6 h-6 text-success-green-600" />
              </div>
              <span className="text-xs font-semibold text-success-green-600 uppercase">App Integrado</span>
            </div>
            
            <h3 className="text-lg font-bold text-gray-800 mb-2">Cobrança Pix</h3>
            <p className="text-sm text-gray-600 mb-4">
              Gere links de cobrança e envie pelo WhatsApp
            </p>

            <div className="bg-gray-50 rounded-lg p-3 mb-4">
              <p className="text-xs text-gray-500 mb-1">A receber</p>
              <p className="text-2xl font-bold text-success-green-600">
                {new Intl.NumberFormat('pt-BR', {
                  style: 'currency',
                  currency: 'BRL',
                }).format(pendingReceivables)}
              </p>
            </div>

            <button
              onClick={handleCollectPix}
              className="w-full bg-success-green-600 text-white font-semibold py-3 px-4 rounded-lg hover:bg-success-green-700 transition-colors flex items-center justify-center space-x-2"
            >
              <MessageCircle className="w-4 h-4" />
              <span>Cobrar Cliente no WhatsApp</span>
            </button>
          </div>

          {/* App 3: Benefícios MEI */}
          <div className="bg-white rounded-2xl shadow-card p-6 hover:shadow-lg transition-all duration-300">
            <div className="flex items-center justify-between mb-4">
              <div className="bg-purple-100 p-3 rounded-xl">
                <Heart className="w-6 h-6 text-purple-600" />
              </div>
              <span className="text-xs font-semibold text-purple-600 uppercase">Benefícios</span>
            </div>
            
            <h3 className="text-lg font-bold text-gray-800 mb-4">Benefícios MEI</h3>

            {/* Health Plan */}
            <div className="mb-4 p-3 bg-green-50 rounded-lg border border-green-200">
              <div className="flex items-center justify-between mb-2">
                <div className="flex items-center space-x-2">
                  <Heart className="w-4 h-4 text-green-600" />
                  <span className="text-sm font-semibold text-gray-800">Plano de Saúde</span>
                </div>
                {healthPlan.active ? (
                  <span className="flex items-center space-x-1 text-xs text-green-600 font-semibold">
                    <CheckCircle className="w-4 h-4" />
                    <span>Ativo</span>
                  </span>
                ) : (
                  <span className="text-xs text-gray-500">Inativo</span>
                )}
              </div>
              <p className="text-xs text-gray-600">{healthPlan.provider}</p>
            </div>

            {/* DAS Status */}
            <div className="p-3 bg-blue-50 rounded-lg border border-blue-200">
              <div className="flex items-center justify-between mb-2">
                <div className="flex items-center space-x-2">
                  <Receipt className="w-4 h-4 text-blue-600" />
                  <span className="text-sm font-semibold text-gray-800">DAS (Imposto)</span>
                </div>
                {dasStatus.paid ? (
                  <span className="flex items-center space-x-1 text-xs text-green-600 font-semibold">
                    <CheckCircle className="w-4 h-4" />
                    <span>Em dia</span>
                  </span>
                ) : (
                  <span className="flex items-center space-x-1 text-xs text-amber-600 font-semibold">
                    <Clock className="w-4 h-4" />
                    <span>Pendente</span>
                  </span>
                )}
              </div>
              <p className="text-xs text-gray-600">Próximo vencimento: {dasStatus.nextDue}</p>
            </div>
          </div>
        </div>

        {/* Quick Stats */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white rounded-xl shadow-card p-6">
            <div className="flex items-center space-x-3 mb-2">
              <DollarSign className="w-5 h-5 text-success-green-600" />
              <span className="text-sm font-semibold text-gray-600">Faturamento Mensal</span>
            </div>
            <p className="text-2xl font-bold text-gray-800">
              {new Intl.NumberFormat('pt-BR', {
                style: 'currency',
                currency: 'BRL',
              }).format(5000)}
            </p>
          </div>

          <div className="bg-white rounded-xl shadow-card p-6">
            <div className="flex items-center space-x-3 mb-2">
              <FileText className="w-5 h-5 text-bank-blue-600" />
              <span className="text-sm font-semibold text-gray-600">Notas Emitidas</span>
            </div>
            <p className="text-2xl font-bold text-gray-800">12</p>
            <p className="text-xs text-gray-500">Este mês</p>
          </div>

          <div className="bg-white rounded-xl shadow-card p-6">
            <div className="flex items-center space-x-3 mb-2">
              <CheckCircle className="w-5 h-5 text-purple-600" />
              <span className="text-sm font-semibold text-gray-600">Status</span>
            </div>
            <p className="text-2xl font-bold text-gray-800">Ativo</p>
            <p className="text-xs text-gray-500">Conta verificada</p>
          </div>
        </div>
      </main>

      {/* Modals */}
      <InvoiceModal 
        isOpen={showInvoiceModal} 
        onClose={() => setShowInvoiceModal(false)} 
      />
      <CreditModal 
        isOpen={showCreditModal} 
        onClose={() => setShowCreditModal(false)}
        creditLimit={creditLimit}
      />
      <WhatsAppModal 
        isOpen={showWhatsAppModal} 
        onClose={() => setShowWhatsAppModal(false)}
        pendingAmount={pendingReceivables}
      />
    </div>
  );
};

export default MeiHub;

