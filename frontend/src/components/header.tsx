import { TrendingUp } from 'lucide-react';

interface HeaderProps {
  onBackToAnalysis?: () => void;
  showBackButton?: boolean;
}

const Header: React.FC<HeaderProps> = ({ onBackToAnalysis, showBackButton = false }) => {
  return (
    <header className="bg-gradient-to-r from-bank-blue-800 to-bank-blue-600 text-white shadow-lg">
      <div className="container mx-auto px-4 py-6">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <div className="bg-white/20 p-2 rounded-lg">
              <TrendingUp className="w-8 h-8" />
            </div>
            <div>
              <h1 className="text-2xl md:text-3xl font-bold">MEI-Mentor</h1>
              <p className="text-bank-blue-100 text-sm md:text-base">
                Painel de Oportunidades
              </p>
            </div>
          </div>
          {showBackButton && onBackToAnalysis ? (
            <button
              onClick={onBackToAnalysis}
              className="bg-white/10 backdrop-blur-sm px-4 py-2 rounded-lg border border-white/20 hover:bg-white/20 transition-colors"
              title="Voltar para tela de análise"
            >
              <p className="text-sm font-medium">← Nova Análise</p>
            </button>
          ) : (
            <div className="hidden md:block">
              <div className="bg-white/10 backdrop-blur-sm px-4 py-2 rounded-lg border border-white/20">
                <p className="text-sm font-medium">Sistema de Análise</p>
              </div>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};

export default Header;

