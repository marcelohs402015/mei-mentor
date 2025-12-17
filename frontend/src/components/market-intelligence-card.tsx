import { Sparkles, Wifi, WifiOff, Star, MapPin, Instagram, Linkedin, Github, Target } from 'lucide-react';
import { MarketIntelligenceResponse } from '@/types';

interface MarketIntelligenceCardProps {
  intelligence: MarketIntelligenceResponse | null;
}

const MarketIntelligenceCard: React.FC<MarketIntelligenceCardProps> = ({ intelligence }) => {
  if (!intelligence) {
    return (
      <div className="bg-gradient-to-br from-gray-50 to-gray-100 border-2 border-gray-200 rounded-2xl p-6 shadow-card">
        <div className="flex items-center space-x-3 mb-4">
          <div className="bg-gray-400 p-3 rounded-xl text-white">
            <Sparkles className="w-6 h-6" />
          </div>
          <h3 className="text-gray-700 text-sm font-semibold uppercase tracking-wide">
            Raio-X de Mercado
          </h3>
        </div>
        <p className="text-gray-500 text-sm">Dados de inteligência de mercado não disponíveis</p>
      </div>
    );
  }

  const getPresenceLevel = (score: number | null): { label: string; stars: number; color: string } => {
    if (!score) {
      return { label: 'Sem dados', stars: 0, color: 'text-gray-500' };
    }
    if (score >= 70) {
      return { label: 'Alta', stars: 3, color: 'text-success-green-600' };
    }
    if (score >= 40) {
      return { label: 'Média', stars: 2, color: 'text-amber-500' };
    }
    return { label: 'Baixa', stars: 1, color: 'text-alert-red-600' };
  };

  const getSocialMediaIcon = (platform: string | null) => {
    if (!platform) return null;
    const lower = platform.toLowerCase();
    if (lower.includes('instagram')) return <Instagram className="w-5 h-5" />;
    if (lower.includes('linkedin')) return <Linkedin className="w-5 h-5" />;
    if (lower.includes('github')) return <Github className="w-5 h-5" />;
    return null;
  };

  const presence = getPresenceLevel(intelligence.digitalPresenceScore);

  return (
    <div className="bg-gradient-to-br from-purple-50 via-purple-100 to-indigo-50 border-2 border-purple-300 rounded-2xl p-6 shadow-card hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1">
      {/* Header */}
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center space-x-3">
          <div className="bg-gradient-to-br from-purple-500 to-indigo-600 p-3 rounded-xl text-white shadow-lg">
            <Sparkles className="w-6 h-6" />
          </div>
          <div>
            <h3 className="text-purple-800 text-sm font-bold uppercase tracking-wide">
              Raio-X de Mercado
            </h3>
            <p className="text-purple-600 text-xs">Análise com Inteligência Artificial</p>
          </div>
        </div>
      </div>

      {/* Business Niche */}
      {intelligence.businessNiche && (
        <div className="mb-4">
          <div className="flex items-center space-x-2 mb-2">
            <Target className="w-4 h-4 text-purple-600" />
            <span className="text-xs font-semibold text-purple-700 uppercase">Atividade Provável</span>
          </div>
          <p className="text-lg font-bold text-purple-900">{intelligence.businessNiche}</p>
        </div>
      )}

      {/* Digital Presence */}
      <div className="mb-4">
        <div className="flex items-center justify-between mb-2">
          <div className="flex items-center space-x-2">
            {presence.stars > 0 ? (
              <Wifi className="w-4 h-4 text-purple-600" />
            ) : (
              <WifiOff className="w-4 h-4 text-gray-400" />
            )}
            <span className="text-xs font-semibold text-purple-700 uppercase">Presença Digital</span>
          </div>
          <div className="flex items-center space-x-1">
            {Array.from({ length: 3 }).map((_, i) => (
              <Star
                key={i}
                className={`w-4 h-4 ${
                  i < presence.stars
                    ? 'fill-current text-yellow-400'
                    : 'text-gray-300'
                }`}
              />
            ))}
            <span className={`ml-2 text-sm font-bold ${presence.color}`}>
              {presence.label}
            </span>
          </div>
        </div>
        {intelligence.digitalPresenceScore !== null && (
          <div className="text-xs text-purple-600">
            Score: {intelligence.digitalPresenceScore}/100
          </div>
        )}
      </div>

      {/* Social Media Info */}
      {(intelligence.socialMediaPlatform || intelligence.socialMediaFollowers) && (
        <div className="mb-4 p-3 bg-white/50 rounded-lg">
          <div className="flex items-center space-x-2 text-sm">
            {getSocialMediaIcon(intelligence.socialMediaPlatform)}
            {intelligence.socialMediaPlatform && (
              <span className="font-medium text-purple-800">{intelligence.socialMediaPlatform}</span>
            )}
            {intelligence.socialMediaFollowers && (
              <span className="text-purple-600">
                • {intelligence.socialMediaFollowers.toLocaleString('pt-BR')} seguidores
              </span>
            )}
          </div>
        </div>
      )}

      {/* Google Maps */}
      {intelligence.hasGoogleMapsPresence && (
        <div className="mb-4 flex items-center space-x-2 text-sm text-purple-700">
          <MapPin className="w-4 h-4" />
          <span>Cadastro no Google Maps</span>
        </div>
      )}

      {/* Maturity */}
      {intelligence.estimatedMaturity && (
        <div className="mb-4">
          <span className="text-xs font-semibold text-purple-700 uppercase">Maturidade: </span>
          <span className="text-sm font-bold text-purple-900">{intelligence.estimatedMaturity}</span>
        </div>
      )}

      {/* Dica de Ouro - Recommended Approach */}
      {intelligence.recommendedApproach && (
        <div className="mt-6 pt-6 border-t-2 border-purple-200">
          <div className="flex items-start space-x-3">
            <div className="bg-gradient-to-br from-yellow-400 to-amber-500 p-2 rounded-lg shadow-md flex-shrink-0">
              <Sparkles className="w-5 h-5 text-white" />
            </div>
            <div className="flex-1">
              <h4 className="text-sm font-bold text-purple-900 mb-2 flex items-center space-x-2">
                <span>💡 Dica da IA</span>
              </h4>
              <p className="text-sm text-purple-800 leading-relaxed italic">
                "{intelligence.recommendedApproach}"
              </p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default MarketIntelligenceCard;

