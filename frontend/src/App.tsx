import { useState } from 'react';
import Header from './components/header';
import SearchBar from './components/search-bar';
import OpportunityDashboard from './components/opportunity-dashboard';
import LoadingSpinner from './components/loading-spinner';
import ErrorMessage from './components/error-message';
import ActivationModal from './components/activation-modal';
import MeiHub from './components/mei-hub';
import { opportunityService } from './services/opportunity.service';
import { OpportunityAnalysisResponse, ApiError } from './types';

type ViewMode = 'analysis' | 'hub';

const App: React.FC = () => {
  const [analysis, setAnalysis] = useState<OpportunityAnalysisResponse | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [viewMode, setViewMode] = useState<ViewMode>('analysis');
  const [showActivationModal, setShowActivationModal] = useState(false);

  const handleSearch = async (cpf: string) => {
    setIsLoading(true);
    setError(null);
    setAnalysis(null);

    try {
      const result = await opportunityService.analyzeByCpf(cpf);
      setAnalysis(result);
    } catch (err) {
      const apiError = err as ApiError;
      setError(apiError.message || 'Erro ao buscar análise de oportunidade');
    } finally {
      setIsLoading(false);
    }
  };

  const handleRetry = () => {
    if (analysis) {
      // Se já temos uma análise, não faz sentido retry
      return;
    }
    setError(null);
  };

  const handleActivate = () => {
    setShowActivationModal(true);
  };

  const handleAccessAccount = () => {
    setShowActivationModal(false);
    setViewMode('hub');
  };

  const handleBackToAnalysis = () => {
    setViewMode('analysis');
    setAnalysis(null);
    setError(null);
  };

  const generateCNPJ = (): string => {
    // Gera um CNPJ fictício formatado
    const random = Math.floor(Math.random() * 1000000000000).toString().padStart(12, '0');
    return `${random.slice(0, 2)}.${random.slice(2, 5)}.${random.slice(5, 8)}/${random.slice(8, 12)}-${Math.floor(Math.random() * 100).toString().padStart(2, '0')}`;
  };

  const getCompanyName = (): string => {
    if (!analysis) return 'Empresa MEI';
    // Tenta extrair nome do market intelligence ou usa padrão
    return analysis.marketIntelligence?.businessNiche 
      ? `${analysis.marketIntelligence.businessNiche.split('/')[0].trim()} MEI`
      : 'Empresa MEI';
  };

  // Render MEI-HUB
  if (viewMode === 'hub' && analysis) {
    return (
      <MeiHub
        companyName={getCompanyName()}
        cnpj={generateCNPJ()}
        creditLimit={analysis.shadowLimit}
        onBack={handleBackToAnalysis}
      />
    );
  }

  // Render Analysis View
  return (
    <div className="min-h-screen bg-gray-50">
      <Header 
        onBackToAnalysis={viewMode === 'hub' ? handleBackToAnalysis : undefined}
        showBackButton={viewMode === 'hub'}
      />
      
      <main>
        <SearchBar onSearch={handleSearch} isLoading={isLoading} />
        
        {isLoading && <LoadingSpinner />}
        
        {error && (
          <div className="container mx-auto px-4 py-8">
            <ErrorMessage message={error} onRetry={handleRetry} />
          </div>
        )}
        
        {analysis && !isLoading && (
          <>
            <OpportunityDashboard analysis={analysis} onActivate={handleActivate} />
            
            <ActivationModal
              isOpen={showActivationModal}
              onClose={() => setShowActivationModal(false)}
              onAccessAccount={handleAccessAccount}
              companyName={getCompanyName()}
              cnpj={generateCNPJ()}
              creditLimit={analysis.shadowLimit}
            />
          </>
        )}
        
        {!analysis && !isLoading && !error && (
          <div className="container mx-auto px-4 py-12">
            <div className="max-w-2xl mx-auto text-center">
              <div className="bg-white rounded-2xl shadow-card p-8">
                <h2 className="text-2xl font-bold text-gray-800 mb-4">
                  Bem-vindo ao MEI-Mentor
                </h2>
                <p className="text-gray-600 leading-relaxed">
                  Digite o CPF do cliente acima para iniciar a análise de oportunidades de formalização MEI.
                  O sistema identificará padrões comerciais nas transações e calculará a economia potencial.
                </p>
              </div>
            </div>
          </div>
        )}
      </main>
      
      <footer className="bg-gray-800 text-gray-300 mt-16 py-8">
        <div className="container mx-auto px-4 text-center">
          <p className="text-sm">
            © 2024 MEI-Mentor. Sistema de Análise de Oportunidades MEI.
          </p>
        </div>
      </footer>
    </div>
  );
};

export default App;

