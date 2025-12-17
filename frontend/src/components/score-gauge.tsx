interface ScoreGaugeProps {
  score: number;
}

const ScoreGauge: React.FC<ScoreGaugeProps> = ({ score }) => {
  const getColor = () => {
    if (score >= 70) return 'text-success-green-600';
    if (score >= 40) return 'text-amber-500';
    return 'text-alert-red-600';
  };

  const getScoreLabel = () => {
    if (score >= 70) return 'Alto Potencial';
    if (score >= 40) return 'Médio Potencial';
    return 'Baixo Potencial';
  };

  const circumference = 2 * Math.PI * 90; // radius = 90
  const offset = circumference - (score / 100) * circumference;

  return (
    <div className="flex flex-col items-center justify-center space-y-4">
      <div className="relative w-48 h-48 md:w-56 md:h-56">
        <svg 
          className="transform -rotate-90 w-full h-full" 
          viewBox="0 0 200 200"
        >
          {/* Background circle */}
          <circle
            cx="100"
            cy="100"
            r="90"
            fill="none"
            stroke="#e5e7eb"
            strokeWidth="16"
          />
          {/* Progress circle */}
          <circle
            cx="100"
            cy="100"
            r="90"
            fill="none"
            stroke={score >= 70 ? '#10b981' : score >= 40 ? '#f59e0b' : '#ef4444'}
            strokeWidth="16"
            strokeDasharray={circumference}
            strokeDashoffset={offset}
            strokeLinecap="round"
            className="transition-all duration-1000 ease-out"
          />
        </svg>
        {/* Score text */}
        <div className="absolute inset-0 flex items-center justify-center">
          <div className="text-center">
            <p className={`text-4xl md:text-5xl font-bold ${getColor()}`}>
              {score}%
            </p>
          </div>
        </div>
      </div>
      <div className="text-center">
        <p className={`text-lg font-semibold ${getColor()}`}>{getScoreLabel()}</p>
        <p className="text-sm text-gray-500 mt-1">Score de Oportunidade</p>
      </div>
    </div>
  );
};

export default ScoreGauge;

