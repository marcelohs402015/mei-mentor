import InfoModal from '../info-modal';
import { MessageCircle, Code, Zap, CheckCircle, ArrowRight, Smartphone } from 'lucide-react';

interface WhatsAppModalProps {
  isOpen: boolean;
  onClose: () => void;
  pendingAmount: number;
}

const WhatsAppModal: React.FC<WhatsAppModalProps> = ({ isOpen, onClose, pendingAmount }) => {
  return (
    <InfoModal
      isOpen={isOpen}
      onClose={onClose}
      title="Cobrança Pix via WhatsApp"
      icon={<MessageCircle className="w-6 h-6 text-white" />}
      iconColor="bg-success-green-600"
    >
      <div className="space-y-6">
        {/* Valor a Receber */}
        <div className="bg-success-green-50 rounded-xl p-4 border-2 border-success-green-200">
          <p className="text-sm text-success-green-700 font-semibold mb-2">Total a Receber</p>
          <p className="text-3xl font-bold text-success-green-900">
            {new Intl.NumberFormat('pt-BR', {
              style: 'currency',
              currency: 'BRL',
            }).format(pendingAmount)}
          </p>
        </div>

        {/* Como Funciona */}
        <div>
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <Zap className="w-5 h-5 text-success-green-600" />
            <span>Como Funciona</span>
          </h3>
          <p className="text-gray-700 leading-relaxed">
            Gere links de cobrança Pix personalizados e envie diretamente pelo WhatsApp para seus clientes. 
            O cliente recebe uma mensagem com o link, clica e paga instantaneamente. Você recebe 
            notificação em tempo real quando o pagamento é confirmado.
          </p>
        </div>

        {/* Fluxo */}
        <div className="bg-gray-50 rounded-xl p-4">
          <h3 className="text-lg font-bold text-gray-800 mb-3 flex items-center space-x-2">
            <Smartphone className="w-5 h-5 text-success-green-600" />
            <span>Fluxo de Cobrança</span>
          </h3>
          <div className="space-y-3">
            <div className="flex items-start space-x-3">
              <div className="bg-success-green-600 text-white rounded-full w-8 h-8 flex items-center justify-center font-bold flex-shrink-0">1</div>
              <div>
                <p className="font-semibold text-gray-800">Você cria a cobrança</p>
                <p className="text-sm text-gray-600">Informe cliente, valor e descrição do serviço</p>
              </div>
            </div>
            <div className="flex items-start space-x-3">
              <div className="bg-success-green-600 text-white rounded-full w-8 h-8 flex items-center justify-center font-bold flex-shrink-0">2</div>
              <div>
                <p className="font-semibold text-gray-800">Link é gerado automaticamente</p>
                <p className="text-sm text-gray-600">Sistema cria QR Code e link Pix único</p>
              </div>
            </div>
            <div className="flex items-start space-x-3">
              <div className="bg-success-green-600 text-white rounded-full w-8 h-8 flex items-center justify-center font-bold flex-shrink-0">3</div>
              <div>
                <p className="font-semibold text-gray-800">Envio via WhatsApp</p>
                <p className="text-sm text-gray-600">Mensagem personalizada é enviada automaticamente</p>
              </div>
            </div>
            <div className="flex items-start space-x-3">
              <div className="bg-success-green-600 text-white rounded-full w-8 h-8 flex items-center justify-center font-bold flex-shrink-0">4</div>
              <div>
                <p className="font-semibold text-gray-800">Cliente paga</p>
                <p className="text-sm text-gray-600">Cliente clica no link e paga via Pix em segundos</p>
              </div>
            </div>
            <div className="flex items-start space-x-3">
              <div className="bg-success-green-600 text-white rounded-full w-8 h-8 flex items-center justify-center font-bold flex-shrink-0">5</div>
              <div>
                <p className="font-semibold text-gray-800">Você recebe notificação</p>
                <p className="text-sm text-gray-600">Confirmação instantânea no MEI-HUB e por WhatsApp</p>
              </div>
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
              <span>Recebimento instantâneo via Pix</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Reduz inadimplência com lembretes automáticos</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Profissionalismo: cliente recebe mensagem personalizada</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Controle total: veja quem pagou e quem está pendente</span>
            </li>
            <li className="flex items-start space-x-2">
              <CheckCircle className="w-5 h-5 text-success-green-600 flex-shrink-0 mt-0.5" />
              <span>Histórico completo de todas as cobranças</span>
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
              <p className="font-semibold mb-1">1. Integração com API Pix:</p>
              <p className="pl-4">
                Integrar com API do Banco Central (Pix) ou gateway de pagamento (ex: Mercado Pago, 
                PagSeguro) para gerar cobranças Pix dinâmicas com QR Code.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">2. Integração WhatsApp Business API:</p>
              <p className="pl-4">
                Usar WhatsApp Business API (Meta) ou serviço intermediário (ex: Twilio, Evolution API) 
                para envio automatizado de mensagens. Requer aprovação do Meta.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">3. Backend (Java/Spring Boot):</p>
              <p className="pl-4">
                Endpoints: <code className="bg-gray-200 px-2 py-1 rounded">POST /api/pix/charge</code> 
                (cria cobrança), <code className="bg-gray-200 px-2 py-1 rounded">POST /api/whatsapp/send</code> 
                (envia mensagem), <code className="bg-gray-200 px-2 py-1 rounded">POST /api/pix/webhook</code> 
                (recebe confirmação de pagamento).
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">4. Frontend (React):</p>
              <p className="pl-4">
                Formulário de cobrança (cliente, valor, descrição). Lista de cobranças pendentes com 
                status (enviada, paga, vencida). Botão "Reenviar" para lembrete.
              </p>
            </div>
            <div>
              <p className="font-semibold mb-1">5. Funcionalidades Adicionais:</p>
              <ul className="pl-4 list-disc list-inside space-y-1">
                <li>Template de mensagens personalizáveis</li>
                <li>Lembretes automáticos (1 dia, 3 dias, 7 dias após vencimento)</li>
                <li>Desconto por pagamento antecipado</li>
                <li>Parcelamento direto no link</li>
                <li>Relatórios de taxa de conversão (enviadas vs pagas)</li>
                <li>Integração com emissor de nota (gera NFS-e após pagamento)</li>
              </ul>
            </div>
          </div>
        </div>

        {/* Alternativas */}
        <div className="bg-blue-50 rounded-xl p-4 border-l-4 border-blue-500">
          <h3 className="text-lg font-bold text-blue-900 mb-2">💡 Alternativas de Implementação</h3>
          <div className="space-y-2 text-sm text-blue-800">
            <p><strong>Opção 1 (Mais Simples):</strong> Usar link de compartilhamento do WhatsApp Web. 
            Gerar mensagem pré-formatada e abrir WhatsApp Web com o texto pronto para envio manual.</p>
            <p><strong>Opção 2 (Intermediária):</strong> Integrar com serviço de SMS/Email primeiro, 
            depois evoluir para WhatsApp quando tiver aprovação da API.</p>
            <p><strong>Opção 3 (Completa):</strong> WhatsApp Business API oficial com webhook de 
            confirmação de leitura e pagamento.</p>
          </div>
        </div>

        {/* Próximos Passos */}
        <div className="bg-success-green-50 rounded-xl p-4">
          <h3 className="text-lg font-bold text-success-green-900 mb-2 flex items-center space-x-2">
            <ArrowRight className="w-5 h-5" />
            <span>Próximos Passos</span>
          </h3>
          <p className="text-success-green-800 text-sm">
            Começar com implementação simples (link de compartilhamento) para MVP, depois evoluir para 
            integração completa com WhatsApp Business API. Esta funcionalidade é um grande diferencial 
            competitivo, pois acelera o recebimento e melhora o relacionamento com clientes.
          </p>
        </div>
      </div>
    </InfoModal>
  );
};

export default WhatsAppModal;

