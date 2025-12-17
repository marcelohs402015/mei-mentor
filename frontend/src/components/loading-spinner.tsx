import { Loader2 } from 'lucide-react';

const LoadingSpinner: React.FC = () => {
  return (
    <div className="flex flex-col items-center justify-center py-12 space-y-4">
      <Loader2 className="w-12 h-12 text-bank-blue-600 animate-spin" />
      <p className="text-gray-600 font-medium">Carregando análise...</p>
    </div>
  );
};

export default LoadingSpinner;

