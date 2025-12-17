import { OpportunityAnalysisResponse } from '@/types';
import OpportunityCard from './opportunity-card';
import ScoreGauge from './score-gauge';
import CTAButton from './cta-button';
import MarketIntelligenceCard from './market-intelligence-card';
import { TrendingUp } from 'lucide-react';

interface OpportunityDashboardProps {
  analysis: OpportunityAnalysisResponse;
  onActivate?: () => void;
}

const OpportunityDashboard: React.FC<OpportunityDashboardProps> = ({ analysis, onActivate }) => {
  const handleCTAClick = () => {
    if (onActivate) {
      onActivate();
    } else {
      alert('Redirecionando para ativação da conta MEI...');
    }
  };

  return (
    <div className="container mx-auto px-4 py-8 space-y-8">
      {/* Cards de Impacto */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <OpportunityCard
          type="loss"
          title="Dinheiro Deixado na Mesa"
          value={analysis.monthlyLoss}
          subtitle="Economia imediata ao virar MEI"
        />
        
        <OpportunityCard
          type="limit"
          title="Limite Disponível Pré-Aprovado"
          value={analysis.shadowLimit}
          subtitle="Seu poder de compra como PJ"
        />
        
        <OpportunityCard
          type="score"
          title="Potencial Empreendedor"
          value={analysis.potentialScore}
          subtitle="Score de oportunidade"
        />
      </div>

      {/* Market Intelligence Card - Raio-X de Mercado */}
      {analysis.marketIntelligence && (
        <div className="w-full">
          <MarketIntelligenceCard intelligence={analysis.marketIntelligence} />
        </div>
      )}

      {/* Score Gauge e Recomendação */}
      <div className="bg-white rounded-2xl shadow-card p-8">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 items-center">
          <div>
            <h2 className="text-2xl font-bold text-gray-800 mb-4 flex items-center space-x-2">
              <TrendingUp className="w-6 h-6 text-bank-blue-600" />
              <span>Análise de Potencial</span>
            </h2>
            <ScoreGauge score={analysis.potentialScore} />
          </div>
          
          <div className="space-y-4">
            <div className="bg-bank-blue-50 border-l-4 border-bank-blue-500 p-6 rounded-lg">
              <h3 className="font-semibold text-gray-800 mb-2">Recomendação</h3>
              <p className="text-gray-700 leading-relaxed">{analysis.recommendation}</p>
            </div>
            
            <div className="bg-gray-50 p-4 rounded-lg">
              <p className="text-sm text-gray-600 mb-2">
                <span className="font-semibold">Receita Identificada:</span>
              </p>
              <p className="text-2xl font-bold text-bank-blue-700">
                {new Intl.NumberFormat('pt-BR', {
                  style: 'currency',
                  currency: 'BRL',
                }).format(analysis.identifiedRevenue)}
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* CTA Button */}
      <div className="flex justify-center">
        <CTAButton onClick={handleCTAClick} />
      </div>
    </div>
  );
};

export default OpportunityDashboard;

