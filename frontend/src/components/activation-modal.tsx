import { CheckCircle, X, ArrowRight } from 'lucide-react';

interface ActivationModalProps {
  isOpen: boolean;
  onClose: () => void;
  onAccessAccount: () => void;
  companyName: string;
  cnpj: string;
  creditLimit: number;
}

const ActivationModal: React.FC<ActivationModalProps> = ({
  isOpen,
  onClose,
  onAccessAccount,
  companyName,
  cnpj,
  creditLimit,
}) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl shadow-2xl max-w-2xl w-full p-8 relative animate-in fade-in zoom-in duration-300">
        {/* Close Button */}
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-gray-400 hover:text-gray-600 transition-colors"
          aria-label="Fechar"
        >
          <X className="w-6 h-6" />
        </button>

        {/* Success Icon */}
        <div className="flex justify-center mb-6">
          <div className="bg-success-green-100 rounded-full p-4">
            <CheckCircle className="w-16 h-16 text-success-green-600" />
          </div>
        </div>

        {/* Title */}
        <h2 className="text-3xl font-bold text-center text-gray-800 mb-4">
          🎉 Conta MEI Ativada com Sucesso!
        </h2>

        {/* Company Info */}
        <div className="bg-gradient-to-r from-bank-blue-50 to-bank-blue-100 rounded-xl p-6 mb-6 border-2 border-bank-blue-200">
          <div className="text-center space-y-2">
            <p className="text-sm text-bank-blue-700 font-semibold uppercase">Empresa</p>
            <p className="text-2xl font-bold text-bank-blue-900">{companyName}</p>
            <p className="text-sm text-bank-blue-600">CNPJ: {cnpj}</p>
          </div>
        </div>

        {/* Credit Limit Info */}
        <div className="bg-gradient-to-r from-success-green-50 to-success-green-100 rounded-xl p-6 mb-6 border-2 border-success-green-200">
          <div className="text-center space-y-2">
            <p className="text-sm text-success-green-700 font-semibold uppercase">Limite Pré-Aprovado</p>
            <p className="text-3xl font-bold text-success-green-900">
              {new Intl.NumberFormat('pt-BR', {
                style: 'currency',
                currency: 'BRL',
              }).format(creditLimit)}
            </p>
            <p className="text-sm text-success-green-600">Capital de Giro Disponível</p>
          </div>
        </div>

        {/* Benefits List */}
        <div className="mb-6 space-y-3">
          <div className="flex items-center space-x-3 text-gray-700">
            <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0" />
            <span>Conta MEI ativada e pronta para uso</span>
          </div>
          <div className="flex items-center space-x-3 text-gray-700">
            <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0" />
            <span>Limite de crédito disponível imediatamente</span>
          </div>
          <div className="flex items-center space-x-3 text-gray-700">
            <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0" />
            <span>Acesso ao MEI-HUB com ferramentas integradas</span>
          </div>
          <div className="flex items-center space-x-3 text-gray-700">
            <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0" />
            <span>Emissor de notas fiscais gratuito</span>
          </div>
        </div>

        {/* CTA Button */}
        <button
          onClick={onAccessAccount}
          className="w-full bg-gradient-to-r from-bank-blue-600 to-bank-blue-700 text-white font-bold py-4 px-6 rounded-xl hover:from-bank-blue-700 hover:to-bank-blue-800 focus:outline-none focus:ring-4 focus:ring-bank-blue-300 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center justify-center space-x-3 text-lg"
        >
          <span>Acessar Minha Conta</span>
          <ArrowRight className="w-5 h-5" />
        </button>
      </div>
    </div>
  );
};

export default ActivationModal;

