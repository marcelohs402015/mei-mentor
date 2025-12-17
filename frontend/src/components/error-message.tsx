import { AlertCircle, RefreshCw } from 'lucide-react';

interface ErrorMessageProps {
  message: string;
  onRetry?: () => void;
}

const ErrorMessage: React.FC<ErrorMessageProps> = ({ message, onRetry }) => {
  return (
    <div className="bg-alert-red-50 border-2 border-alert-red-200 rounded-xl p-6 max-w-2xl mx-auto">
      <div className="flex items-start space-x-4">
        <div className="bg-alert-red-500 p-2 rounded-lg">
          <AlertCircle className="w-6 h-6 text-white" />
        </div>
        <div className="flex-1">
          <h3 className="text-alert-red-800 font-bold text-lg mb-2">
            Erro ao buscar análise
          </h3>
          <p className="text-alert-red-700 mb-4">{message}</p>
          {onRetry && (
            <button
              onClick={onRetry}
              className="inline-flex items-center space-x-2 bg-alert-red-600 text-white px-4 py-2 rounded-lg hover:bg-alert-red-700 transition-colors font-medium"
            >
              <RefreshCw className="w-4 h-4" />
              <span>Tentar novamente</span>
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ErrorMessage;

