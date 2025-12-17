import InfoModal from '../info-modal';
import { FileText, Code, Zap, CheckCircle, ArrowRight } from 'lucide-react';

interface InvoiceModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const InvoiceModal: React.FC<InvoiceModalProps> = ({ isOpen, onClose }) => {
  return (
    <InfoModal
      isOpen={isOpen}
      onClose={onClose}
      title="Emissor de Nota Fiscal"
      icon={<FileText className="w-6 h-6 text-white" />}
      iconColor="bg-bank-blue-600"
    >
      <div className="space-y-6">
        {/* Como Funciona */}
        <div>
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <Zap className="w-5 h-5 text-bank-blue-600" />
            <span>Como Funciona</span>
          </h3>
          <p className="text-gray-700 leading-relaxed">
            O Emissor de Nota Fiscal permite que você emita Notas Fiscais de Serviços Eletrônicas (NFS-e) 
            de forma rápida e simples, diretamente do seu MEI-HUB. Todas as notas são armazenadas e 
            organizadas automaticamente.
          </p>
        </div>

        {/* Benefícios */}
        <div>
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <CheckCircle className="w-5 h-5 text-success-green-600" />
            <span>Benefícios</span>
          </h3>
          <ul className="space-y-2 text-gray-700">
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Emissão instantânea de NFS-e sem sair do sistema</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Histórico completo de todas as notas emitidas</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Integração automática com a Prefeitura</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Envio automático por email para o cliente</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Relatórios e controle de faturamento</span>
            </li>
          </ul>
        </div>

        {/* Implementação Técnica */}
        <div className="bg-gray-50 rounded-xl p-4 border-l-4 border-bank-blue-500">
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <Code className="w-5 h-5 text-bank-blue-600" />
            <span>Como Implementar</span>
          </h3>
          <div className="space-y-3 text-sm text-gray-700">
            <div>
              <p className="font-semibold mb-1">1. Integração com API da Prefeitura:</p>
              <p className="pl-4">
                Integrar com a API de emissão de NFS-e da Prefeitura local (ex: Nota Carioca, 
                Nota Paulista). Cada município tem sua própria API.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">2. Backend (Java/Spring Boot):</p>
              <p className="pl-4">
                Criar endpoint <code className="bg-gray-200 px-2 py-1 rounded">POST /api/invoices</code> que 
                recebe dados do serviço, valida, e chama a API da Prefeitura. Armazenar nota no banco.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">3. Frontend (React):</p>
              <p className="pl-4">
                Formulário de emissão com campos: cliente, descrição, valor, data. Após envio, 
                exibir PDF da nota e opção de reenvio por email.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">4. Funcionalidades Adicionais:</p>
              <ul className="pl-4 list-disc list-inside space-y-1">
                <li>Template de notas personalizáveis</li>
                <li>Assinatura digital automática</li>
                <li>Notificações de status (emitida, cancelada, rejeitada)</li>
                <li>Dashboard com estatísticas de faturamento</li>
              </ul>
            </div>
          </div>
        </div>

        {/* Próximos Passos */}
        <div className="bg-bank-blue-50 rounded-xl p-4">
          <h3 className="text-lg font-bold text-bank-blue-900 mb-2 flex items-center space-x-2">
            <ArrowRight className="w-5 h-5" />
            <span>Próximos Passos</span>
          </h3>
          <p className="text-bank-blue-800 text-sm">
            Esta funcionalidade pode ser desenvolvida em fases: primeiro integração básica com uma 
            Prefeitura piloto, depois expansão para outras cidades. O banco pode oferecer como 
            diferencial competitivo para novos clientes MEI.
          </p>
        </div>
      </div>
    </InfoModal>
  );
};

export default InvoiceModal;

