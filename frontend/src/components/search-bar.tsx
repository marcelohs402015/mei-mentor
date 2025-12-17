import { useState } from 'react';
import { Search, Loader2 } from 'lucide-react';

interface SearchBarProps {
  onSearch: (cpf: string) => void;
  isLoading: boolean;
}

const SearchBar: React.FC<SearchBarProps> = ({ onSearch, isLoading }) => {
  const [cpf, setCpf] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    const cleanCpf = cpf.replace(/\D/g, '');

    if (cleanCpf.length !== 11) {
      setError('CPF deve conter 11 dígitos');
      return;
    }

    onSearch(cleanCpf);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.replace(/\D/g, '');
    
    if (value.length <= 11) {
      setCpf(value);
      setError('');
    }
  };

  const formatCpf = (value: string): string => {
    const clean = value.replace(/\D/g, '');
    if (clean.length <= 3) return clean;
    if (clean.length <= 6) return `${clean.slice(0, 3)}.${clean.slice(3)}`;
    if (clean.length <= 9) return `${clean.slice(0, 3)}.${clean.slice(3, 6)}.${clean.slice(6)}`;
    return `${clean.slice(0, 3)}.${clean.slice(3, 6)}.${clean.slice(6, 9)}-${clean.slice(9, 11)}`;
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="max-w-2xl mx-auto">
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
              <Search className="h-5 w-5 text-gray-400" />
            </div>
            <input
              type="text"
              value={formatCpf(cpf)}
              onChange={handleChange}
              placeholder="Digite o CPF do cliente (apenas números)"
              className="w-full pl-12 pr-4 py-4 text-lg border-2 border-gray-300 rounded-xl focus:ring-2 focus:ring-bank-blue-500 focus:border-bank-blue-500 outline-none transition-all shadow-sm"
              disabled={isLoading}
              maxLength={14}
            />
          </div>
          
          {error && (
            <p className="text-alert-red-600 text-sm font-medium">{error}</p>
          )}

          <button
            type="submit"
            disabled={isLoading || cpf.replace(/\D/g, '').length !== 11}
            className="w-full bg-gradient-to-r from-bank-blue-600 to-bank-blue-700 text-white font-semibold py-4 px-6 rounded-xl hover:from-bank-blue-700 hover:to-bank-blue-800 focus:outline-none focus:ring-2 focus:ring-bank-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-all shadow-lg hover:shadow-xl flex items-center justify-center space-x-2"
          >
            {isLoading ? (
              <>
                <Loader2 className="w-5 h-5 animate-spin" />
                <span>Analisando...</span>
              </>
            ) : (
              <>
                <Search className="w-5 h-5" />
                <span>Buscar Oportunidades</span>
              </>
            )}
          </button>

          <div className="text-center text-sm text-gray-500 mt-4">
            <p>Exemplos: 12345678901, 98765432100, 11122233344</p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SearchBar;

