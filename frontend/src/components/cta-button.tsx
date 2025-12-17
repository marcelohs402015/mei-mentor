import { Sparkles, ArrowRight } from 'lucide-react';

interface CTAButtonProps {
  onClick: () => void;
  disabled?: boolean;
}

const CTAButton: React.FC<CTAButtonProps> = ({ onClick, disabled = false }) => {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className="group relative w-full md:w-auto bg-gradient-to-r from-success-green-600 to-success-green-700 text-white font-bold py-5 px-8 rounded-2xl hover:from-success-green-700 hover:to-success-green-800 focus:outline-none focus:ring-4 focus:ring-success-green-300 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-300 shadow-xl hover:shadow-2xl transform hover:scale-105 flex items-center justify-center space-x-3 text-lg"
    >
      <Sparkles className="w-6 h-6 group-hover:rotate-12 transition-transform" />
      <span>Ativar Conta MEI & Resgatar Limite</span>
      <ArrowRight className="w-5 h-5 group-hover:translate-x-1 transition-transform" />
    </button>
  );
};

export default CTAButton;

